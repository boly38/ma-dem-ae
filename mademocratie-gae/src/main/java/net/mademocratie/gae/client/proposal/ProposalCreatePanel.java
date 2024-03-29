package net.mademocratie.gae.client.proposal;

import com.google.inject.Inject;
import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.service.IManageProposal;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.logging.Logger;

public class ProposalCreatePanel extends Panel{

    private static final long serialVersionUID = 6343641923671807395L;

    private final static Logger LOGGER = Logger.getLogger(ProposalCreatePanel.class.getName()); 

    private Page parentPage;
    
    private FeedbackPanel feedback;

    private Form<Proposal> propForm;
    
    @Inject
    private IManageProposal manageProposal;
    
    public ProposalCreatePanel(String id, Page parentPage) {
        super(id);
        this.setParentPage(parentPage);
        initComponents();
    }
    
    private void initComponents() {
	    createPropForm();
    }
    
//    private StringResourceModel getStringResourceModel(java.lang.String key) {
//        // BVA fix Localizer warning : cf. https://issues.apache.org/jira/browse/WICKET-990
//        return new StringResourceModel(key, this, null);
//    }

    private void createPropForm() {
    	CompoundPropertyModel<Proposal> formModel = new CompoundPropertyModel<Proposal>(new Proposal());
        propForm = new Form<Proposal>("propForm", formModel);

        feedback = new FeedbackPanel("feedback");
	    feedback.setOutputMarkupId(true);
	    propForm.add(feedback);

        
        TextField<String> propTitle = new TextField<String>("title");
        propTitle.setRequired(true);
        propTitle.add(StringValidator.maximumLength(140));
        propForm.add(propTitle);
        
        TextArea<String> propContent = new TextArea<String>("content");
        propContent.setRequired(false);
        propForm.add(propContent);
        

        createFormButtons(propForm);

        add(propForm);
    }

    private void createFormButtons(Form<Proposal> appform) {

        // Add first application button
    	AjaxSubmitLink addButton = new AjaxSubmitLink("addPropButton") {
            /**
			 * serialUID
			 */
			private static final long serialVersionUID = -8438481612594719399L;

			@Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    Citizen citizen = CitizenSession.get().getCitizen();
                    manageProposal.addProposal((Proposal) form.getModelObject(), citizen);
                	form.clearInput();
                    HomePage homePage = new HomePage();
                    Session.get().info("Your proposal was added");
                    setResponsePage(homePage);
                } catch (Exception ee) {
                	String errMsg = "Unable to add proposal : " + ee.getMessage();
                	LOGGER.warning(errMsg);
                	error(errMsg);
                    // repaint the feedback panel so that it is hidden
                    target.add(feedback);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
            	LOGGER.warning("submit Error");
                // repaint the feedback panel so that it is hidden
                target.add(feedback);            	
            }

        };
        appform.add(addButton);

    }

	public Page getParentPage() {
		return parentPage;
	}

	public void setParentPage(Page parentPage) {
		this.parentPage = parentPage;
	}
}
