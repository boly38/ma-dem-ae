package com.appspot.mademocratie.client;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IRepository;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

public class ProposalCreatePanel extends Panel{

    private static final long serialVersionUID = 6343641923671807395L;

    private final static Logger LOGGER = Logger.getLogger(ProposalCreatePanel.class.getName()); 

    private Page parentPage;
    
    private FeedbackPanel feedback;

    private Form<Proposal> propForm;
    
    @Inject
    private IRepository<Proposal> proposalRepo;
    
    public ProposalCreatePanel(String id, Page parentPage) {
        super(id);
        this.setParentPage(parentPage);
        initComponents();
    }
    
    private void initComponents() {
    	//      feedback = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this));
	    feedback = new FeedbackPanel("feedback");
	    feedback.setOutputMarkupId(true);
	    add(feedback);

	    createPropForm();
    }
    
//    private StringResourceModel getStringResourceModel(java.lang.String key) {
//        // BVA fix Localizer warning : cf. https://issues.apache.org/jira/browse/WICKET-990
//        return new StringResourceModel(key, this, null);
//    }

    private void createPropForm() {
    	CompoundPropertyModel<Proposal> formModel = new CompoundPropertyModel<Proposal>(new Proposal());
        propForm = new Form<Proposal>("propForm", formModel);
      
        TextField<String> propTitle = new TextField<String>("title");
        propTitle.setRequired(true);
        propTitle.add(StringValidator.maximumLength(140));
        propForm.add(propTitle);

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
            	UserService userService = UserServiceFactory.getUserService();
            	User user = userService.getCurrentUser();                
                Proposal newProposal = (Proposal) form.getModelObject();
                newProposal.setDate(new Date());
                newProposal.setAuthor(user);
                try {
                	proposalRepo.persist(newProposal);
                	form.clearInput();
                	// repaint the page 
                	target.add(parentPage);
                } catch (Exception ee) {
                	String errMsg = "Unable to add proposal : " + ee.getMessage();
                	LOGGER.warning(errMsg);
                	// TODO FIX the feedback in this case ! 
                	feedback.error(errMsg);
                    // repaint the feedback panel so that it is hidden
                    target.add(feedback);                 	
                }

                // This causes a redirect to a clean page and URL, rather than rendering to the state of the
                // current page which we don't care about here.
                setResponsePage(HomePage.class);                
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
