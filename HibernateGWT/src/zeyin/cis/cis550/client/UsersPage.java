package zeyin.cis.cis550.client;

import java.util.List;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;

import zeyin.cis.cis550.shared.UserResult;

/**
 * Renders a table of actors
 * 
 * @author zives
 *
 */
public class UsersPage {
	List<UserResult> users;
	String query;
	
	final Button againButton = new Button("Return");

	CellTable<UserResult> userTable = new CellTable<UserResult>();
	ListDataProvider<UserResult> userProvider;
	
	/**
	 * These are definitions for the CellTable of what to put in
	 * each column
	 */
	
	
	TextColumn<UserResult> nameColumn = new TextColumn<UserResult>() {

		@Override
		public String getValue(UserResult object) {
			return object.getName();
		}
		
	};
	TextColumn<UserResult> followsColumn = new TextColumn<UserResult>() {

		@Override
		public String getValue(UserResult object) {
			StringBuilder ret = new StringBuilder();
			
			boolean first = true;
			for (UserResult ur: object.getFollows()) {
				if (first)
					first = false;
				else
					ret.append(", ");
				
				ret.append(ur.getName());
			}
			return ret.toString();
		}
		
	};

	/**
	 * Constructor for actors page
	 * 
	 * @param query the term we searched for
	 * @param actors list of actors returned
	 */
	public UsersPage(String query, List<UserResult> actors) {
		this.users = actors;
		this.query = query;
		
		// We can add style names to widgets
		againButton.addStyleName("sendButton");

		// clear the contents of the main page
		RootPanel.get("messageContainer").clear();
		RootPanel.get("nameFieldContainer").clear();
		RootPanel.get("sendButtonContainer").clear();
		RootPanel.get("errorLabelContainer").clear();

		// Add our label, table, and button
		RootPanel.get("messageContainer").add(new Label("Users with name " + query + ":"));
		RootPanel.get("nameFieldContainer").add(userTable);
		RootPanel.get("errorLabelContainer").add(againButton);
		
		// Format the table to show 2 columns from Users
		userTable.addColumn(nameColumn, "Name");
		userTable.addColumn(followsColumn, "Follows");
		
		// Create a data provider that connects from the List of Actors
		// to the actual CellTable
		userProvider = new ListDataProvider<UserResult>();
		userProvider.addDataDisplay(userTable);
	}
	
	/**
	 * Does the majority of the active work for the Actors page,
	 * by populating the table and handling button clicks
	 */
	public void doWork() {
		// The ListDataProvider has an internal list; populate it
		List<UserResult> output = userProvider.getList();
		output.addAll(users);

		// If the user clicks "Return" we switch back to the index page
		againButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				IndexPage ip = new IndexPage();
				ip.doWork();
			}
			
		});
	}
	
}
