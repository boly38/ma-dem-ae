package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.exception.ChangePasswordException;
import net.mademocratie.gae.server.exception.DeprecatedActivationLinkException;
import net.mademocratie.gae.server.exception.MaDemocratieException;
import net.mademocratie.gae.server.exception.WrongActivationLinkException;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import java.util.logging.Logger;

/**
 * ActivatePage
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ActivatePage extends PageTemplate {
    private final static Logger LOGGER = Logger.getLogger(ActivatePage.class.getName());

    public static final String ACTIVATION_LINK_IS_DEPRECATED = "Your activation link is deprecated, please login or register again, or contact admin.";
    public static final String ACTIVATION_LINK_IS_INVALID = "Your activation link is invalid, please login or register again, or contact admin.";
    public static final String ACTIVATION_SUCCESS = "Your account has been activated.";
    public static final String PARAM_AK = "ak";
    public static final String PARAM_CID = "cid";

    // ~services
    @Inject
    private IManageCitizen manageCitizen;

    private Long currentCitizenId = null;
    private PassForm passForm;

    /**
     * PageTemplate constructor
     *
     * @param params - page parameters map
     */
    public ActivatePage(final PageParameters params) {
        super(params);
        this.params = params;
        initComponents();
        setOutputMarkupId(true);
        activationChecks();
        initPassComponents();
    }

    private void initComponents() {
        // Create feedback panel and add to page
        add(new FeedbackPanel("feedback"));

        // Add password form to page
        passForm = new PassForm("passForm");
        hidePassForm();
        add(passForm);
    }

    private void initPassComponents() {

    }

    private void activationChecks() {
        currentCitizenId = params.get(PARAM_CID).toLong();
        String activationKey = params.get(PARAM_AK).toString();

        try {
            manageCitizen.activateCitizenByKey(currentCitizenId, activationKey);
            success(ACTIVATION_SUCCESS);
            showPassForm();
        } catch (DeprecatedActivationLinkException e) {
            hidePassForm();
            error(ACTIVATION_LINK_IS_DEPRECATED);
        } catch (WrongActivationLinkException e) {
            hidePassForm();
            error(ACTIVATION_LINK_IS_INVALID);
        }
    }


    private void changeCitizenPassword(String password, String passwordConfirmation) throws ChangePasswordException {
        try {
            if (password==null || !password.equals(passwordConfirmation)) {
                throw new ChangePasswordException("Password and confirmation doesn't match.");
            }
            manageCitizen.changeCitizenPassword(currentCitizenId, password);
        } catch (MaDemocratieException e) {
            error(e.getMessage());
        }
    }

    private void showPassForm() {
        passForm.setVisible(true);
    }

    private void hidePassForm() {
        passForm.setVisible(false);
    }

    /**
     * Password form
     */
    public final class PassForm extends Form<Void>
    {
        private static final String PASSWORD = "password";
        private static final String PASSWORD_CONFIRMATION= "passwordConfirmation";

        // El-cheapo model for form
        private final ValueMap properties = new ValueMap();

        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public PassForm(final String id) {
            super(id);

            // Attach textfield components that edit properties map model
            add(new PasswordTextField(PASSWORD, new PropertyModel<String>(properties, PASSWORD)));
            add(new PasswordTextField(PASSWORD_CONFIRMATION, new PropertyModel<String>(properties, PASSWORD_CONFIRMATION)));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
            LOGGER.info("password ok");
            try {
                changeCitizenPassword(getPassword(), getPasswordConfirmation());
                passForm.setVisible(false);
                addFeedbackSuccess("Your password has been changed, please sign in.");
                setResponsePage(getApplication().getHomePage());
            } catch (ChangePasswordException e) {
                error(e.getMessage());
            }
        }

        public String getPassword() {
            return (String) properties.get(PASSWORD);
        }

        public String getPasswordConfirmation() {
            return (String) properties.get(PASSWORD_CONFIRMATION);
        }
    }
}

