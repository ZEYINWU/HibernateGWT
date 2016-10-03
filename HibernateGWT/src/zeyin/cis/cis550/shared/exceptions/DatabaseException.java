package zeyin.cis.cis550.shared.exceptions;

/**
 * A simple exception that can be passed back
 * to the client if there is a database error
 * 
 * @author zives
 *
 */
public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String err) {
		super(err);
	}
	
	public DatabaseException() {
		super();
	}
}
