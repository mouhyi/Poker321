package Server.userModule;

import java.rmi.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This interface defines all of the remote features offered by the User service
 * 
 * @author mouhyi
 * 
 */
public interface RemoteUser extends Remote {

	/**
	 * This method implements the sign up functionality
	 * 
	 * @author mouhyi
	 */
	public boolean signup(UserObject user) throws RemoteException, SQLException;

	/**
	 * This method implements the login functionality
	 * 
	 * @author mouhyi
	 */
	public UserObject login (String email, String password) throws RemoteException, SQLException;

	/**
	 * This method logs out the current user
	 * 
	 * @author mouhyi
	 */
	public int logout(UserObject user) throws RemoteException, SQLException;

	/**
	 * This method sends the users password to his email address
	 * 
	 * @author mouhyi
	 */
	public void recoverPassword(String email) throws RemoteException, SQLException;

	/**
	 * This method enables the user to update his profile
	 * 
	 * @author mouhyi
	 */
	public int editProfile(UserObject user) throws RemoteException, SQLException ;

	/**
	 * This method enables the user to add chips to his account
	 * 
	 * @author mouhyi
	 */
	public int purchaseChips(UserObject user, double ammount)
			throws RemoteException, SQLException;

	/**
	 * This method returns the user's friend list
	 * 
	 * @author mouhyi
	 */
	public ArrayList<UserObject> getFriends(int u_id) throws RemoteException, SQLException;

	/**
	 * @param name
	 *            or email
	 * @author mouhyi
	 */
	public void addFriend(String friend) throws RemoteException, SQLException;

	/**
	 * @author mouhyi
	 */
	public void deleteFriend() throws RemoteException, SQLException;
}
