package zeyin.cis.cis550.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import zeyin.cis.cis550.client.events.QueryRequestEvent;
import zeyin.cis.cis550.shared.UserResult;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyApp implements EntryPoint, QueryRequestEvent.Handler {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side lookup service.
	 */
	private final MyServiceAsync myService = GWT
			.create(MyService.class);
	
	/**
	 * The "event bus" lets different modules in a GWT application communicate,
	 * typically to send event messages.  In our case we'll use it to trigger
	 * requests for Remote Procedure Calls.
	 */
	public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Register our object as a handler for a kind of event --
		// a request-query event
		EVENT_BUS.addHandler(QueryRequestEvent.TYPE, this);
		
		// Spawn off a new page 
		IndexPage index = new IndexPage();
		index.doWork();
	}

	/**
	 * This is a handler for an event fired on the "event bus",
	 * which takes a request for a query and does a Remote Procedure
	 * Call to the server
	 */
	@Override
	public void processRequestForUser(String name,
			AsyncCallback<List<UserResult>> callback) {
		myService.getUsersWithName(name, callback);
	}

}