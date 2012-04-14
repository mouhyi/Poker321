package Server.userModule;

import java.rmi.RemoteException;

import java.sql.SQLException;
import java.util.ArrayList;
import Client.IUserClient;
import Data.UserData;

/**
 * Implementation class of the remote interface
 * 
 * @author mouhyi
 * 
 */
public class UserImpl extends java.rmi.server.UnicastRemoteObject implements
		RemoteUser {

	
	private ArrayList<IUserClient> userClients;
	
	// Implementations must have an explicit constructor in order
	// to declare the RemoteException exception
	public UserImpl() throws RemoteException {
		super();
		userClients = new ArrayList<IUserClient>();
	}

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
	@Override
	public int addFriend(int userId, int friendId) throws RemoteException,
			SQLException {
		return UserData.addFriend(userId, friendId);

	}

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
	@Override
	public int deleteFriend(int userId, int friendId) throws SQLException {
		return UserData.deleteFriend(userId, friendId);
		
	}

	/**
	 * This method enables the user to update his profile
	 * 
	 * @param user
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	@Override
	public int editProfile(UserObject user) throws RemoteException,
			SQLException {
		return UserData.updateUser(user);

	}

	/**
	 * Rturn user's friends
	 * 
	 * @param u_id
	 *            : user Id
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	@Override
	public ArrayList<UserObject> getFriends(int u_id) throws RemoteException,
			SQLException {
		return UserData.getFriends(u_id);

	}

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
	@Override
	public UserObject login(String email, String password)
			throws RemoteException, SQLException {

		UserObject user = null;
		// check if user exists
		if (UserData.authenticate(email, password) != 0)
			return null;
		// set user to online
		int userId = UserData.getId(email);
		user = UserData.getUserObject(userId);
		user.setOnline(true);
		UserData.updateUser(user);
		return user;

	}

	/**
	 ** 
	 * This method logs out the current user
	 * 
	 * @param user
	 * @return logged out UserObject
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	// TODO: change the parameter to userId
	@Override
	public int logout(UserObject user) throws RemoteException, SQLException {
		user.setOnline(false);
		return UserData.updateUser(user);

	}

	/**
	 * Enables the user to add chips to his account
	 * 
	 * @param user
	 * @param ammount
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	@Override
	public int purchaseChips(UserObject user, double ammount)
			throws RemoteException, SQLException {
		if (ammount <= 0)
			return -1;
		user.setChips(user.getChips() + ammount);
		return UserData.updateUser(user);
	}

	@Override
	public void recoverPassword(String email) throws RemoteException {
		// TODO figure out how to send password to email in java

	}

	/**
	 * Registers a new user
	 * 
	 * @param user
	 * @return 0 on success, -1 on failure
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	@Override
	public int signup(UserObject user) throws RemoteException, SQLException {
		return Data.UserData.createUser(user);

	}

	/**
	 * Searchs a user by email
	 * 
	 * @param email or username
	 * @return UserObject corresponding to email
	 * @throws RemoteException
	 * @throws SQLException
	 * @author mouhyi
	 */
	public UserObject getUserObject(String email)
			throws RemoteException, SQLException {
		int userId = UserData.getId(email);
		return UserData.getUserObject(userId);
	}
	
	/**
	 * Searchs users by Id
	 * @param userId
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public UserObject getUserObject(int userId) throws RemoteException,
			SQLException {
		return UserData.getUserObject(userId);
	}

	@Override
	public void registerUser(IUserClient ucl) throws RemoteException {
		userClients.add(ucl);
		
	}
	/**
	 * This creates a 2D array where the first layer is the usernames in ranked order, the second is the number of games won
	 * and the third is the amount earned while playing
	 *
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	@Override
	public String[][] leaderBoardDisplay() throws SQLException, RemoteException{
		return Data.statistics.createLeaderBoard();
	}
}
