package zeyin.cis.cis550.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import zeyin.cis.cis550.shared.UserResult;

/**
 * The async counterpart of <code>MyService</code>.
 */
public interface MyServiceAsync {

	void getUsersWithName(String name, AsyncCallback<List<UserResult>> callback);
}
