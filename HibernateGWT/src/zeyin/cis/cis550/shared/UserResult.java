package zeyin.cis.cis550.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This basic class is a Data Transfer Object for
 * representing an IMDB Actor
 * 
 * @author zives
 *
 */
public class UserResult implements IsSerializable {
	int id;
	String name;
	Set<UserResult> follows;
	
	/**
	 * Standard empty-constructor
	 */
	public UserResult() {
		super();
		id = 0;
		name = "";
		follows = new HashSet<UserResult>();
	}
	
	/**
	 * Constructor for a User with known values
	 * 
	 * @param id
	 * @param name
	 */
	public UserResult(int id, String name) {
		this();
		this.id = id;
		this.name = name;
//		this.follows = follows;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setFollows(Set<UserResult> follows) {
		this.follows = follows;
	}

	public List<UserResult> getFollows() {
		List<UserResult> ret = new ArrayList<UserResult>(this.follows);
//		 for(User usr:this.follows){
//    		 ret.add(new UserResult(usr.getId(),usr.getName(),new HashSet<User>(usr.getFollows())));
//    	 }
		return ret;
	}
	
}
