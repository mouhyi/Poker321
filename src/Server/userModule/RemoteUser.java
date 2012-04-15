package Server.userModule;

import java.rmi.*;
import java.sql.SQLException;
import java.util.ArrayList;

import Client.IUserClient;

/**
 * This interface defines all of the remote features offered by the User service
 * 
 * @author mouhyi
 * 
 */
public interface RemoteUser extends Remote {

	/**
	 * This method registers a new user
	 * 
	 * @param user
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int signup(UserObject user) throws RemoteException, SQLException;
	
	
	
	/**
	 * Registers user with RMI server for callbacks 
	 */
	public void registerUser(IUserClient ucl) throws RemoteException;

	/**
	 * This method implements the login functionality
	 * 
	 * @param email
	 * @param password
	 * @return UserObject corresponding to email
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public UserObject login(String email, String password)
			throws RemoteException, SQLException;

	/**
	 * This method logs out the current user
	 * 
	 * @param user
	 * @return logged out UserObject
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int logout(UserObject user) throws RemoteException, SQLException;

	/**
	 * This method sends the users password to his email address
	 * 
	 * @author mouhyi
	 */
	public void recoverPassword(String email) throws RemoteException,
			SQLException;

	/**
	 * This method enables the user to update his profile
	 * 
	 * @param user
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int editProfile(UserObject user) throws RemoteException,
			SQLException;

	/**
	 * This method enables the user to add chips to his account
	 * 
	 * @param user
	 * @param ammount
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int purchaseChips(UserObject user, double ammount)
			throws RemoteException, SQLException;

	/**
	 * This method returns the user's friend list
	 * 
	 * @param u_id
	 * @return ArrayList<UserObject> containing users friends
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public ArrayList<UserObject> getFriends(int u_id) throws RemoteException,
			SQLException;

	/**
	 * Add a friendship relationship between two users
	 * 
	 * @param userId
	 * @param friendId
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int addFriend(int userId, int friendId) throws RemoteException,
			SQLException;

	/**
	 * Delete a friendship relationship between two users
	 * 
	 * @param userId
	 * @param friendId
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public int deleteFriend(int userId, int friendId) throws RemoteException,
			SQLException;
	/**
	 * search a user by email
	 * 
	 * @param email
	 * @return UserObject corresponding to email
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public UserObject getUserObject(String email)
			throws RemoteException, SQLException;
			
	public UserObject getUserObject(int userId)
			throws RemoteException, SQLException;


	/**
	 * Get statistics data
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public String[][] leaderBoardDisplay() throws SQLException, RemoteException;
}
