package zeyin.cis.cis550.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import zeyin.cis.cis550.shared.UserResult;
import zeyin.cis.cis550.shared.exceptions.DatabaseException;

/**
 * The client side stub for the RPC service.
 * 
 */
@RemoteServiceRelativePath("datalake")
public interface MyService extends RemoteService {
	List<UserResult> getUsersWithName(String name) throws IllegalArgumentException, DatabaseException;
}
