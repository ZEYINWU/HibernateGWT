package zeyin.cis.cis550.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import zeyin.cis.cis550.client.events.QueryRequestEvent;
import zeyin.cis.cis550.shared.UserResult;

/**
 * This class handles the rendering and actions
 * related to the "index" page for our application.
 * It basically shows a text form and a button.
 * 
 * @author zives
 *
 */
/**
 * This class handles the rendering and actions
 * related to the "index" page for our application.
 * It basically shows a text form and a button.
 * 
 * @author zives
 *
 */
public class IndexPage {
	final Button sendButton = new Button("Submit");
	final TextBox nameField = new TextBox();
	final Label errorLabel = new Label();

	/**
	 * Put the basic controls in place
	 */
	public IndexPage() {
		nameField.setText("User");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		RootPanel.get("messageContainer").clear();
		RootPanel.get("nameFieldContainer").clear();
		RootPanel.get("sendButtonContainer").clear();
		RootPanel.get("errorLabelContainer").clear();
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("messageContainer").add(new Label("Please enter a user name:"));
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
	}

	// Create a handler for the sendButton and nameField
	class MyHandler implements ClickHandler, KeyUpHandler {
		/**
		 * Fired when the user clicks on the sendButton.
		 */
		public void onClick(ClickEvent event) {
			sendNameToServer();
		}

		/**
		 * Fired when the user types in the nameField.
		 */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				sendNameToServer();
			}
		}

		/**
		 * Send the name from the nameField to the server and wait for a response.
		 * 
		 * Does this by firing an event on the event bus, and passing along a
		 * handler for the response.
		 */
		private void sendNameToServer() {
			// First, we validate the input.
			errorLabel.setText("");
			final String textToServer = nameField.getText();

			// Then, we send the input to the server.
			sendButton.setEnabled(false);
			
			// trigger a query via the event bus (control goes back to MyApp)
			MyApp.EVENT_BUS.fireEvent(new QueryRequestEvent(textToServer,
					new AsyncCallback<List<UserResult>>() {
				
				// On error, call the error dialog routine
				public void onFailure(Throwable caught) {
					postErrorDialog();
				}

				// On successful retrieval of actors, create an actors page
				public void onSuccess(List<UserResult> result) {
					UsersPage ap = new UsersPage(textToServer, result);
					ap.doWork();
				}
			}));
		}
	}
	
	public void postErrorDialog() {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");

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

		// Show the RPC error message to the user
		dialogBox
				.setText("Remote Procedure Call - Failure");
		dialogBox.center();
		closeButton.setFocus(true);
	}

	/**
	 * Main "active" logic:  create the handler for keypresses and buttons
	 */
	public void doWork() {
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}


}
