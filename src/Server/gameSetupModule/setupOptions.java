package Server.gameSetupModule;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.gameModule.Game;
import Server.userModule.UserImpl;
import Server.userModule.UserObject;

public class setupOptions {

	/**
	 * This class has methods related to the various setup options in starting a
	 * game, that can be used in the game lobby.
	 * 
	 * @author Peter
	 **/
	 
	 	/**
	 * Checks if there is a game with the host's suggested name and then creates
	 * it if there is not such a game, sends an error message if there is not
	 * such a game
	 * 
	 * @param friend
	 * @param user
	 * @param currentTable
	 */


	public GameTable createNewGameTable(int selectedAnte, UserObject host,
			String suggestedName) throws SQLException {
		ArrayList<Player> playerList = new ArrayList<Player>();
		hostPlayerObject = Player(host.getId());
		playerList.add(hostPlayerObject);
		GameTable newGameTable = new GameTable(selectedAnte, host,
				suggestedName, playerList);
		return newGameTable;

	}

	/**
	 * This sends a message to the selected friend
	 * 
	 * @param friend
	 * @param user
	 * @param currentTable
	 * @author Peter
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public void inviteSelectedFriend(UserObject friend, UserObject user,
			GameTable currentTable) {
		//pushNotifications inviteFriend = new pushNotifications(
		//		"Hey you should join this game with" + user.getName(), 0, user
		//					.getName(), friend, currentTable);
		//	inviteFriend.sendNotification();
		//TODO: Need to figure out pushNotifications
	}

	/**
	 * This gets the online friends of the user and returns and ArrayList of
	 * them
	 * 
	 * @param user
	 * @throws SQLException
	 * @throws RemoteException
	 * @author Peter
	 */
	private ArrayList<UserObject> getOnlineFriends(UserObject user)
			throws RemoteException, SQLException {
		ArrayList<UserObject> onlineUsers = new ArrayList<UserObject>();
		UserImpl userGameSetupImpl = new UserImpl();
		ArrayList<UserObject> userFriends = userGameSetupImpl.getFriends(user
				.getId());
		for (UserObject element : userFriends) {
			if (element.isOnline()) {
				onlineUsers.add(element);
			}
		}
		return onlineUsers;
	}


	/**
	 * Creates a list of usernames of the friends that are online for display in
	 * a table
	 * 
	 * @param host
	 * @throws RemoteException
	 * @throws SQLException
	 * @author Peter
	 */
	public ArrayList<String> getOnlineFriendNames(UserObject host)
			throws RemoteException, SQLException {

		ArrayList<UserObject> onlineUsers = getOnlineFriends(host);
		ArrayList<String> onlineUsernames = new ArrayList<String>();
		for (UserObject element : onlineUsers) {
			onlineUsernames.add(element.getName());
		}
		return onlineUsernames;
	}

	/**
	 * This gets the users that are currently in the game
	 * 
	 * @param currentGameTable
	 * @author Peter
	 */
	public ArrayList<String> getUsersInGame(GameTable currentGameTable) {
		ArrayList<String> userNamesInGame = new ArrayList<String>();
		for (UserObject element : currentGameTable.getPlayers()) {
			userNamesInGame.add(element.getName());
		}
		return userNamesInGame;

	}

	/**
	 * This adds a user to the game returns true if successful
	 * 
	 * @param u_id
	 * @param game_id
	 * @return
	 * @author Peter
	 */
	public ArrayList<UserObject> addUserToGame(UserObject user,
			GameTable desiredTable) {
		return desiredTable.addPlayer(user);
	}


	/**
	 * This deletes the user from the game
	 * 
	 * @param user
	 * @param game
	 * @return
	 * @author Peter
	 */
	public ArrayList<UserObject> deleteUserFromGame(UserObject user,
			GameTable game) {
		return game.removePlayer(user);
	}

	
	/**
	 * From a drop down menu the user will select a level of ante this changes
	 * the level of ante of the game, returns chosen level
	 * 
	 * @param selectedLevel
	 * @param game
	 * @return
	 * @author Peter
	 */
	public int changeLevelOfAnte(int selectedLevel, GameTable game) {
		return game.changeAnte(selectedLevel);
	}


}
