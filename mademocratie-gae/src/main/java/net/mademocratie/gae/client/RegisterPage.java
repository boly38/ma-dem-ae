package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.server.service.IManageCitizen;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class RegisterPage extends PageTemplate {
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

        // Add sign-in form to page
        add(new RegisterForm("registerForm"));
    }

    /**
     * Sign in form
     */
    public final class RegisterForm extends Form<Void>
    {
        private static final String USERNAME = "username";

        // El-cheapo model for form
        private final ValueMap properties = new ValueMap();

        /**
         * Constructor
         *
         * @param id
         *            id of the form component
         */
        public RegisterForm(final String id)
        {
            super(id);

            // Attach textfield components that edit properties map model
            add(new TextField<String>(USERNAME, new PropertyModel<String>(properties, USERNAME)));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit()
        {
            // register TODO
        }

        /**
         * @return
         */
        private String getUsername()
        {
            return properties.getString(USERNAME);
        }
    }
}