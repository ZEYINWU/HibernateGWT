package zeyin.cis.cis550.client.events;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

import zeyin.cis.cis550.shared.UserResult;

/**
 * This represents an event to be passed along the EventBus.
 * The particular event is that "someone" is requesting a query
 * for actors by lastname
 *  
 * @author zives
 *
 */
public class QueryRequestEvent extends GwtEvent<QueryRequestEvent.Handler> {
	// Query content
	private String name;
	
	// Whom to call when results are returned
	private AsyncCallback<List<UserResult>> callback; 
	
	/**
	 * A request event for a new query
	 * 
	 * @param lastname Actor last name to look up
	 * @param callback Whom to call with returned results
	 */
	public QueryRequestEvent(String name, AsyncCallback<List<UserResult>> callback) {
		this.name = name;
		this.callback = callback;
	}
	
	/**
	 * GwtEvents need to make clear what their type and handler type is
	 */
	public static Type<QueryRequestEvent.Handler> TYPE = 
			new Type<QueryRequestEvent.Handler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
	
	/**
	 * When an event is fired, we call this method
	 */
	@Override
	protected void dispatch(Handler handler) {
		handler.processRequestForUser(name, callback);
	}
	
	/**
	 * An event handler for the query request event
	 * 
	 * @author zives
	 *
	 */
	public static interface Handler extends EventHandler {
		public void processRequestForUser(String name, AsyncCallback<List<UserResult>> callback);
	}


}