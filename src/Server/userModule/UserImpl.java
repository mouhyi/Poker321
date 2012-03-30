package Server.userModule;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Data.UserData;

public class UserImpl extends java.rmi.server.UnicastRemoteObject implements
		RemoteUser {

	// Implementations must have an explicit constructor in order
	// to declare the RemoteException exception
	public UserImpl() throws RemoteException {
		super();
	}

	@Override
	public void addFriend(String friend) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteFriend() throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * This method enables the user to update his profile
	 * 
	 * @return 0 on success, -1 on failure
	 * @author mouhyi
	 */
	@Override
	public int editProfile(UserObject user) throws RemoteException,
			SQLException {
		return UserData.updateUser(user);

	}

	/**
	 * Get user's FriendsList
	 * 
	 * @return ArrayList of friends UserObjects
	 * @author mouhyi
	 * 
	 */
	@Override
	public ArrayList<UserObject> getFriends(int u_id) throws RemoteException,
			SQLException {
		return UserData.getFriends(u_id);

	}

	/**
	 * Log in a registered user
	 * 
	 * @return UserObject corresponding to email
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
	 * Log user Out
	 * 
	 * @param user
	 * @throws RemoteException
	 * @throws SQLException
	 * @return 0 on success, -1 on failure
	 * @author mouhyi
	 */
	@Override
	public int logout(UserObject user) throws RemoteException, SQLException {
		user.setOnline(false);
		return UserData.updateUser(user);

	}

	/**
	 * Add chips to user's account
	 * 
	 * @param ammount
	 *            >0
	 * @return 0 on success, -1 on failure
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
	 * Register a new user
	 * 
	 * @return 0 on success, -1 on failure
	 * @author mouhyi
	 */
	@Override
	public boolean signup(UserObject user) throws RemoteException, SQLException {
		return Data.UserData.createUser(user);

	}

}
