package com.appspot.mademocratie.client;

import java.util.logging.Logger;

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

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IManageProposal;
import com.google.inject.Inject;

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
	    Session.get().cleanupFeedbackMessages();
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
                LOGGER.info("submit Add");
                try {
                	manageProposal.addProposal((Proposal) form.getModelObject());
                	form.clearInput();
                } catch (Exception ee) {
                	String errMsg = "Unable to add proposal : " + ee.getMessage();
                	LOGGER.warning(errMsg);
                	// TODO FIX the feedback in this case ! 
                	feedback.error(errMsg);
                    // repaint the feedback panel so that it is hidden
                    target.add(feedback);                 	
                }
                String successMsg = "Your proposal was added";
                HomePage homePage = new HomePage();
                Session.get().info(successMsg);
                setResponsePage(homePage);                
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
