package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
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
            // register TODO
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
        private boolean showButton = false;
        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public RegisterUsingGoogleForm(final String id) {
            super(id);
            add(new Button("gg-button") {
                @Override
                public boolean isVisible() {
                    return showButton;
                }
            });
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
            LOGGER.info("register using google");
            // register TODO
        }
    }
}