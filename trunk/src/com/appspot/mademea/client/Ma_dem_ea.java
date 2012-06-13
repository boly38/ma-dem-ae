package com.appspot.mademea.client;

import java.util.List;

import com.appspot.mademea.shared.Proposal;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	
	private final ProposalServiceAsync proposalService = GWT
			.create(ProposalService.class);

	  private VerticalPanel mainPanel = new VerticalPanel();
	  private FlexTable proposalsFlexTable = new FlexTable();
	  private HorizontalPanel addPanel = new HorizontalPanel();
	  private Label proposalLabel = new Label("Input you proposal");
	  private TextBox newProposalTextBox = new TextBox();
	  private Button addProposalButton = new Button("Add your proposal");
	  private Label feedbackLabel = new Label();
	
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

	    // Assemble Main panel.
	    mainPanel.add(feedbackLabel);
	    mainPanel.add(addPanel);
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
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
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
	
	
	/*
	// ----------------------------------------------
	// The message displayed to the user when the server cannot be reached or
	// returns an error.
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	// Create a remote service proxy to talk to the server-side Greeting service.
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	// This is the entry point method.
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("Roger");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			// Fired when the user clicks on the sendButton.
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			// Fired when the user types in the nameField.
		    public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			// Send the name from the nameField to the server and wait for a response.
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	*/
}
