package net.mademocratie.gae.client;/**
 * Created with IntelliJ IDEA.
 * User: bricesteph
 * Date: 14/10/12
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

/**
 * SignInPage
 * <p/>
 * Last update  : $
 * Last author  : $
 *
 * @version : $
 */
public class SignInPage extends PageTemplate {
    // ~services
    @Inject
    private IManageCitizen manageCitizen;
    /**
     * Constructor
     */
    public SignInPage() {
        super(null);
        initComponents();
    }

    private void initComponents() {
        // Create feedback panel and add to page
        add(new FeedbackPanel("feedback"));

        // Add sign-in form to page
        add(new SignInForm("signInForm"));
    }

    /**
     * Sign in form
     */
    public final class SignInForm extends Form<Void>
    {
        private static final String USERNAME = "username";
        private static final String PASSWORD = "password";

        // El-cheapo model for form
        private final ValueMap properties = new ValueMap();

        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public SignInForm(final String id)
        {
            super(id);

            // Attach textfield components that edit properties map model
            add(new TextField<String>(USERNAME, new PropertyModel<String>(properties, USERNAME)));
            add(new PasswordTextField(PASSWORD, new PropertyModel<String>(properties, PASSWORD)));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit()
        {
            // Get session info
            CitizenSession session = getMySession();

            // Sign the user in
            if (manageCitizen.signInCitizen(session, getUsername(), getPassword()))
            {
                /*
                if (!continueToOriginalDestination())
                {
                    setResponsePage(getApplication().getHomePage());
                }
                 */
            }
            else
            {
                // Get the error message from the properties file associated with the Component
                String errmsg = getString("loginError", null, "Unable to sign you in");

                // Register the error message with the feedback panel
                error(errmsg);
            }
        }

        /**
         * @return
         */
        private String getPassword()
        {
            return properties.getString(PASSWORD);
        }

        /**
         * @return
         */
        private String getUsername()
        {
            return properties.getString(USERNAME);
        }

        /**
         * @return
         */
        private CitizenSession getMySession()
        {
            return (CitizenSession)getSession();
        }
    }
}