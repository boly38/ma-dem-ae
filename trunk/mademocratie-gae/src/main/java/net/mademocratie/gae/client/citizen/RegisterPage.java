package net.mademocratie.gae.client.citizen;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import net.mademocratie.gae.client.ActivatePage;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import java.util.logging.Logger;

public class RegisterPage extends PageTemplate {
    private final static Logger LOGGER = Logger.getLogger(RegisterPage.class.getName());
    // ~services
    @Inject
    private IManageCitizen manageCitizen;
    /**
     * Constructor
     */
    public RegisterPage() {
        super(null);
        initComponents();
    }

    private void initComponents() {
        // Create feedback panel and add to page
        add(new FeedbackPanel("feedback"));

        WebMarkupContainer helloAnon = new WebMarkupContainer("header-info");
        add(helloAnon);

        // Add register form to page
        add(new RegisterForm("registerForm"));

        // Add register form to page
        add(new RegisterUsingGoogleForm("registerUsingGoogleForm"));
    }


    private String getNextStepUrl(Citizen jrc) {
        PageParameters activateParameters = new PageParameters();
        activateParameters.add(ActivatePage.PARAM_AK, jrc.getCitizenStateData());
        activateParameters.add(ActivatePage.PARAM_CID, jrc.getId());
        String absoluteUrl = RequestCycle.get().getUrlRenderer().renderFullUrl(
                Url.parse(urlFor(ActivatePage.class, activateParameters).toString()));
        return absoluteUrl;
    }

    private void registerCitizen(String pseudo, String email, User googleUser) throws RegisterFailedException {
        Citizen justRegisteredCitizen = null;
        if (googleUser != null) {
            manageCitizen.register(pseudo, googleUser);
            return;
        }
        // not google user : need to activate the account
        justRegisteredCitizen = manageCitizen.register(pseudo, email);
        try {
            manageCitizen.registerNotifyCitizen(justRegisteredCitizen, getNextStepUrl(justRegisteredCitizen));
        } catch (MaDemocratieException e) {
            error(e.getMessage());
        }
    }

    /**
     * Sign in form
     */
    public final class RegisterForm extends Form<Void>
    {
        private static final String PSEUDO = "pseudo";
        private static final String EMAIL = "email";

        // El-cheapo model for form
        private final ValueMap properties = new ValueMap();

        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public RegisterForm(final String id) {
            super(id);

            // Attach textfield components that edit properties map model
            add(new TextField<String>(PSEUDO, new PropertyModel<String>(properties, PSEUDO)));
            add(new TextField<String>(EMAIL, new PropertyModel<String>(properties, EMAIL)));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
            LOGGER.info("register email=" + getEmail() + " pseudo=" + getPseudo());
            try {
                registerCitizen(getPseudo(), getEmail(), null);
                addFeedbackSuccess("register succeeded, check your email.");
                setResponsePage(getApplication().getHomePage());
            } catch (RegisterFailedException e) {
                error(e.getMessage());
            }
        }

        private String getEmail() {
            return properties.getString(EMAIL);
        }

        private String getPseudo()
        {
            return properties.getString(PSEUDO);
        }
    }

    /**
     * Sign in form
     */
    public final class RegisterUsingGoogleForm extends Form<Void> {
        private User googleUser = null;
        private boolean pseudoUpdated = false;
        private static final String PSEUDO = "pseudo";
        private final ValueMap properties = new ValueMap();
        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public RegisterUsingGoogleForm(final String id) {
            super(id);

            // Attach textfield components that edit properties map model
            add(new TextField<String>(PSEUDO, new PropertyModel<String>(properties, PSEUDO)));

            googleUser = manageCitizen.getGoogleUser();
            if (googleUser != null) {
                if (!pseudoUpdated) {
                    properties.put(PSEUDO, googleUser.getNickname());
                    pseudoUpdated = true;
                }
            }

            WebMarkupContainer googleIn = new WebMarkupContainer("googleIn");
            add(googleIn);
            WebMarkupContainer googleOut = new WebMarkupContainer("googleOut");
            add(googleOut);

            googleIn.setVisible(googleUser != null);
            googleOut.setVisible(googleUser == null);

            googleIn.add(new Label("gguser", googleUser != null ?
                        googleUser.getNickname()
                        : ""));

            String loginUrl = manageCitizen.getGoogleLoginURL("/" + getRequest().getClientUrl());
            String logoutUrl = manageCitizen.getGoogleLogoutURL("/" + getRequest().getClientUrl());

            ExternalLink signIn = new ExternalLink("signIn-google", loginUrl);
            googleOut.add(signIn);

            ExternalLink signOut = new ExternalLink("signOut-google", logoutUrl);
            googleIn.add(signOut);

            add(new Button("gg-button") {

                @Override
                public boolean isEnabled() {
                    return googleUser != null;
                }
            });

        }

        private String getPseudo() {
            return properties.getString(PSEUDO);
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
            if (googleUser == null) return;
            LOGGER.info("register " + getPseudo() + " using google " + googleUser.getEmail());
            try {
                registerCitizen(getPseudo(), googleUser.getEmail(), googleUser);
                addFeedbackSuccess("registration succeeded.");
                manageCitizen.signInGoogleCitizen();
                setResponsePage(getApplication().getHomePage());
            } catch (RegisterFailedException e) {
                error(e.getMessage());
            }
        }
    }
}