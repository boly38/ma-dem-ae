package com.appspot.mademea.client;

import java.util.List;
import java.util.logging.Logger;

import com.appspot.mademea.shared.AuthInfo;
import com.appspot.mademea.shared.Proposal;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ma_dem_ea implements EntryPoint {
    private final static Logger LOGGER = Logger.getLogger(Ma_dem_ea.class.getName()); 
	
	private final ProposalServiceAsync proposalService = GWT
			.create(ProposalService.class);
	private final SecurityServiceAsync securityService = GWT
			.create(SecurityService.class);

	  private VerticalPanel mainPanel = new VerticalPanel();

	  private Label proposalsTableLabel = new Label("Last 20 proposals :");
	  private FlexTable proposalsFlexTable = new FlexTable();
	  
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private Label proposalLabel = new Label("Input you proposal");
	  private TextBox newProposalTextBox = new TextBox();
	  private Button addProposalButton = new Button("Add your proposal");
	  
	  private Label feedbackLabel = new Label();
	  
	  private HorizontalPanel welcomePanel = new HorizontalPanel();
	  private Label helloLabel = new Label();
	  private Anchor authLink = new Anchor();
	
	private void initTabHeader() {
	    // Create table for proposals
		proposalsFlexTable.setText(0, 0, "Proposal");
		proposalsFlexTable.setText(0, 1, "who");
	    proposalsFlexTable.setText(0, 2, "When");
	}
	
	public void onModuleLoad() {
	    // Create table for proposals
		initTabHeader();

	    // Assemble Add Stock panel.
	    addPanel.add(proposalLabel);
	    addPanel.add(newProposalTextBox);
	    addPanel.add(addProposalButton);

	    welcomePanel.add(helloLabel);
	    welcomePanel.add(authLink);
	    
	    // Assemble Main panel.
	    mainPanel.add(welcomePanel);
	    mainPanel.add(feedbackLabel);
	    mainPanel.add(addPanel);
	    mainPanel.add(proposalsTableLabel);
	    mainPanel.add(proposalsFlexTable);
	    
	    // Associate the Main panel with the HTML host page.
	    RootPanel.get("proposalsList").add(mainPanel);

	    

	    // Listen for mouse events on the Add button.
	    addProposalButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        addProposal();
	      }
	    });

	    // Listen for keyboard events in the input box.
	    newProposalTextBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
	         addProposal();
	        }
	      }
	    });	
	    
	    updateTable();	
	    // Move cursor focus to the input box.
	    newProposalTextBox.setFocus(true);	    
	}
	
	private void updateWelcomePanel() {
		LOGGER.info("updateWelcomePanel");
	    // Welcome panel
		securityService.getAuthInfo("/", new AsyncCallback<AuthInfo>() {
			public void onFailure(Throwable caught) {
				feedbackLabel.setText("unable to get authent info : " + caught.getMessage());
			}

			public void onSuccess(AuthInfo authInfos) {
				authLink.setHref(authInfos.getToggleAuthentUrl());
			    if (authInfos.isAuthenticated()) {
				      helloLabel.setText("Welcome " + authInfos.getNickname() + " (your contributions will be shown with this nick) ");
				      authLink.setText(" (logout)");
				    } else {
					  helloLabel.setText("Anonymous, you could ");
					  authLink.setText(" sign in");	    	
				    }				
			}
			
		});

	}
	
	private void updateTable() {
		proposalService.getProposals(new AsyncCallback<List<Proposal>>() {
			public void onSuccess(List<Proposal> props) {
				proposalsFlexTable.removeAllRows();
				proposalsFlexTable.removeAllRows();
				initTabHeader();				
				for (Proposal p : props) {
					int row = proposalsFlexTable.getRowCount();
					proposalsFlexTable.setText(row, 0, p.getTitle());
					proposalsFlexTable.setText(row, 1, p.getAuthor());
					proposalsFlexTable.setText(row, 2, p.getCreationDate().toString());
				}
			    updateWelcomePanel();
			}

			public void onFailure(Throwable caught) {
				feedbackLabel.setText("unable to update table : " + caught.getMessage());
			}
		});
	}
	
	  /**
	   * Add proposal to FlexTable. Executed when the user clicks the addProposalButton or
	   * presses enter in the newProposalTextBox.
	   */
	  private void addProposal() {
		  final String proposalTitle = newProposalTextBox.getText();
		  proposalService.addProposal(proposalTitle, "", new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							feedbackLabel.setText("unable to add proposal : " + caught.getMessage());
						}

						public void onSuccess(Void result) {
							updateTable();
							newProposalTextBox.setText("");
						}
					});

	  }
}
