package com.appspot.mademea.client;

import java.util.List;
import java.util.logging.Logger;

import com.appspot.mademea.client.data.ProposalRecord;
import com.appspot.mademea.shared.AuthInfo;
import com.appspot.mademea.shared.Proposal;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ma_dem_ea implements EntryPoint {
    private final static Logger LOGGER = Logger.getLogger(Ma_dem_ea.class.getName()); 
	
	private final ProposalServiceAsync proposalService = GWT
			.create(ProposalService.class);
	private final SecurityServiceAsync securityService = GWT
			.create(SecurityService.class);

	
	  private VLayout mainPanel = new VLayout(2);

	  private Label proposalsTableLabel = new Label("Last 20 proposals :");
	  private ListGrid proposalsGrid = new ListGrid();
	  
	  private HLayout addLayout = new HLayout(20);
	  private DynamicForm addForm = new DynamicForm();
	  // private Label proposalLabel = new Label("Input you proposal");
	  private TextItem newProposalTextBox = new TextItem();
	  private Button addProposalButton = new Button("Add your proposal");
	  
	  private Label feedbackLabel = new Label();
	  
	  private HTMLFlow welcomeBanner = new HTMLFlow();
	  private HLayout welcomePanel = new HLayout();
	  private HTMLFlow authLink = new HTMLFlow();
	  
	  private final static int STANDARD_WIDTH = 500;
	
	private void initProposalsGrid() {
		proposalsGrid.setWidth(STANDARD_WIDTH);  
		proposalsGrid.setHeight(500);  
		proposalsGrid.setShowAllRecords(true);
		
        ListGridField titleField = new ListGridField("proposal", "Proposal");  
        ListGridField whoField = new ListGridField("who", "Who");  
        ListGridField whenField = new ListGridField("when", "When");  
        proposalsGrid.setFields(titleField, whoField, whenField);  
        proposalsGrid.setCanResizeFields(true);
	}

	private void updateProposalsGrid() {
		LOGGER.info("");
		proposalService.getProposals(new AsyncCallback<List<Proposal>>() {
			public void onSuccess(List<Proposal> props) {
				ProposalRecord[] proposalRecords = new ProposalRecord[props.size()];
				int index = 0;
				for (Proposal p : props) {
					proposalRecords[index++] = new ProposalRecord(p.getTitle(), p.getAuthor(), p.getCreationDate().toString());
				}
				 proposalsGrid.setData(proposalRecords);
 			     // Move cursor focus to the input box.
			}
	
			public void onFailure(Throwable caught) {
				feedbackLabel.setContents("unable to update table : " + caught.getMessage());
			}
		});
	}
	
	public void onModuleLoad() {
		
        String welcomeHtml = "<div style='align:center'><h1>Ma-Dem-AE</h1>"
        				   + "<p>Welcome on Ma-Dem-AE<sup><a href=\"http://code.google.com/p/ma-dem-ae/\">a gg-code project</a></sup> !</p>"
        				   + "<p>This site is under construction...</p><br/><br/></div>";
        welcomeBanner.setContents(welcomeHtml);
        welcomeBanner.setWidth(STANDARD_WIDTH);
        
        
        authLink.setWidth(STANDARD_WIDTH);
	    welcomePanel.addMember(authLink);
	    welcomePanel.setWidth(STANDARD_WIDTH);
	    welcomePanel.setStyleName("welcomePanel");
	    // welcomePanel.setShowEdges(true);

	    // Create grid for proposals
		initProposalsGrid();

	    // Assemble Add Proposal form.
        newProposalTextBox.setTitle("Input your proposal");  
        newProposalTextBox.setRequired(true);  
        newProposalTextBox.setDefaultValue("");
        newProposalTextBox.setWidth(300);
        
        addForm.setFields(newProposalTextBox);
        addForm.setTitleOrientation(TitleOrientation.TOP);
        addForm.setWidth(300);
        // addForm.setShowEdges(true);
        addLayout.addMember(addForm);
        addLayout.addMember(addProposalButton);
        addLayout.setStyleName("addPanel");
        addLayout.setWidth(STANDARD_WIDTH);
        // addLayout.setShowEdges(true);
        // addLayout.setAlign(VerticalAlignment.CENTER);
	    
	    // Assemble Main panel.
	    mainPanel.addMember(welcomeBanner);
	    mainPanel.addMember(welcomePanel);
	    mainPanel.addMember(feedbackLabel);
	    mainPanel.addMember(addLayout);
	    mainPanel.addMember(proposalsTableLabel);
	    mainPanel.addMember(proposalsGrid);
	    mainPanel.setWidth(STANDARD_WIDTH);
	    mainPanel.setStyleName("mainPanel");
	    // mainPanel.setWidth100();
	    // mainPanel.setAlign(Alignment.CENTER);
	    /* sample modal window
	    IButton btn = new IButton("Click me");
	    btn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final Window winModal = new Window();  
                winModal.setWidth(360);  
                winModal.setHeight(115);  
                winModal.setTitle("Modal Window");  
                winModal.setShowMinimizeButton(false);  
                winModal.setIsModal(true);  
                winModal.setShowModalMask(true);  
                winModal.centerInPage();  
                winModal.addCloseClickHandler(new CloseClickHandler() {  
                    public void onCloseClick(CloseClickEvent event) {  
                        winModal.destroy();  
                    }  
                });  
                winModal.addItem(new Label("modal window"));  
                winModal.show();  ;				
			}
	    });
	    mainPanel.addMember(btn);
	    */

	    // Listen for mouse events on the Add button.
	    addProposalButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        addProposal();
	      }
	    });

	    /* TO FIX
	    // Listen for keyboard events in the input box.
	    newProposalTextBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getKeyName().equals("Enter")) {
	         addProposal();
	        }
	      }
	    });	
	    */
	    
	    // draw main panel
	    mainPanel.draw();
	    // Associate the Main panel with the HTML host page.
	    // RootPanel.get("proposalsList").add(mainPanel);
	    // load datas
	    updateProposalsGrid();	
	    newProposalTextBox.focusInItem();	    
	    updateWelcomePanel();
	}
	
	private void updateWelcomePanel() {
		LOGGER.info("updateWelcomePanel");
	    // Welcome panel
		securityService.getAuthInfo("/", new AsyncCallback<AuthInfo>() {
			public void onFailure(Throwable caught) {
				feedbackLabel.setContents("unable to get authent info : " + caught.getMessage());
			}

			public void onSuccess(AuthInfo authInfos) {
				String url = authInfos.getToggleAuthentUrl();
			    if (authInfos.isAuthenticated()) {
				      authLink.setContents("Welcome " + authInfos.getNickname() + "&#160;<a href=" + url 
				    		  + ">(logout)</a><br/>(your contributions will be shown with this nick)");
				    } else {
					  authLink.setContents("Hi anonymous, you could <a href=" + url + ">sign in</a>");	    	
				    }				
			}
			
		});

	}

	  /**
	   * Add proposal to FlexTable. Executed when the user clicks the addProposalButton or
	   * presses enter in the newProposalTextBox.
	   */
	  private void addProposal() {
		  final String proposalTitle = newProposalTextBox.getValueAsString();
		  proposalService.addProposal(proposalTitle, "", new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							feedbackLabel.setContents("unable to add proposal : " + caught.getMessage());
						}

						public void onSuccess(Void result) {
							updateProposalsGrid();
							newProposalTextBox.setValue("");
							newProposalTextBox.focusInItem();
						}
					});

	  }
}
