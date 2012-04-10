package GUI;

import Client.UserClient;

import java.util.ArrayList;
import javax.swing.ImageIcon;

import Server.gameModule.GameServer;
import Server.gameModule.IGameTable;
import Server.gameModule.Player;
import Server.userModule.UserObject;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * 
 * This class contains all the methods that are called by the GUI to access the
 * data stored in the server.
 * 
 * @author mattvertescher & Peter
 */
public class GUIClient {

	private static String IP = "192.168.1.101"; // specifies which IP to connect
												// to
	private int userId; // keeps a local copy of the user ID
	private UserClient currentUserClient = new UserClient(); // sets up RMI
																// connection
	private UserObject currentUser; // keeps a local copy of the logged on user
	private GameClient currentGameTableClient = new GameClient();
	private IGameTable usersGameTable;
	private PlayerClient currentPlayerClient;

	/**
	 * This a constructor for the GUIClient class which retrieves data from the
	 * server and displays it in the GUI
	 */
	public GUIClient() {

	}

	public GUIClient(String ip) {

	}

	// #BeginLoginandUserMethods
	/**
	 * This allows the user to change his username if he is logged on
	 * 
	 * @param newUsername
	 * @return: true if successful, false if not
	 * @author Peter
	 */
	public boolean setUsername(String newUsername) throws RemoteException,
			SQLException {
		if (this.currentUser != null) {
			this.currentUser.setName(newUsername);
			currentUserClient.getUserProxy().editProfile(currentUser);
			return true;
		}

		return false;
	}

	/**
	 * This gets the username of the current user
	 * 
	 * @return: either username, or message saying could not acquire name
	 * @author Peter
	 */
	public String getUsername() {

		if (currentUser != null) {
			return this.currentUser.getName();
		}

		return "Could not get name.";
	}

	/**
	 * This allows the user to purchase more chips
	 * 
	 * @param amount
	 * @return
	 * @author Peter
	 */
	public boolean purchaseChips(double amount) {
		if (amount < 0.0 || amount > 999999999.9) {
			return false;
		}
		int i = currentUserClient.getUserProxy().purchaseChips(currentUser,
				amount);
		if (i == 0)
			return true;

		return false;
	}

	/**
	 * Returns the amount of money a user has
	 * 
	 * @param username
	 * @return
	 */
	public String getUsersWorth(String email) throws RemoteException,
			SQLException {
		String worth = "Broke";
		UserObject desiredUser = currentUserClient.getUserProxy()
				.getUserObject(email);
		worth = "" + desiredUser.getChips();
		// Returns the worth of a particular player

		return worth;
	}

	/**
	 * Method tells server that a user has logged out.
	 * 
	 * @return: boolean, successful or not
	 */
	public boolean logout() throws RemoteException, SQLException {
		int i = currentUserClient.getUserProxy().logout(currentUser);
		currentUser = null;
		if (i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Setter for email.
	 * 
	 * @return
	 * @author Peter
	 */
	public boolean setEmail(String email) throws RemoteException, SQLException {

		currentUser.setEmail(email);
		int i = currentUserClient.getUserProxy().editProfile(currentUser);
		if (i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Getter for email.
	 * 
	 * @return
	 * @author Peter
	 */
	public String getEmail() {
		return currentUser.getEmail();
	}

	/**
	 * Setter for password.
	 * 
	 * @param password
	 * @return
	 * @author Peter
	 */
	public boolean setPassword(String password) throws RemoteException,
			SQLException {
		currentUser.setPassword(password);
		int i = currentUserClient.getUserProxy().editProfile(currentUser);
		if (i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the password.
	 * 
	 * @return
	 * @author Peter
	 */
	public String getPassword() {

		return currentUser.getPassword();
	}

	/**
	 * Setter for account number.
	 * 
	 * @param accountNumber
	 * @return
	 */
	public boolean setAccountNumber(String accountNumber) {

		// Sets account number

		return true;
	}

	/**
	 * Getter for account number.
	 * 
	 * @return account number string
	 */
	public String getAccountNumber() {

		return "BF34NFs3Ffvt345";
	}

	/**
	 * This returns a list of the friends usernames of a certain user
	 * 
	 * @param username
	 * @return
	 */
	public String[] getFriends(String username) throws RemoteException,
			SQLException {
		userId = currentUser.getId();
		ArrayList<UserObject> friends = currentUserClient.getUserProxy()
				.getFriends(userId);
		String[] friendsNames = new String[friends.size()];
		int i = 0;
		for (UserObject element : friends) {
			friendsNames[i] = element.getName();
			i++;
		}
		System.out.println("Get list of friends for user," + username
				+ ", from server");

		return friendsNames;

	}

	/**
	 * Adds the selected user as a friend to the logged on user
	 * 
	 * @param friend
	 * @return: true or false if the add was successful
	 */
	public boolean addFriend(String friend) throws RemoteException,
			SQLException {
		UserObject friendObject = currentUserClient.getUserProxy()
				.getUserObject(friend); // need getUserObject from username
		int i = currentUserClient.getUserProxy().addFriend(userId,
				friendObject.getId());
		System.out.println(currentUser.getName() + " wants to add " + friend
				+ " as a friend");
		if (i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the current user is friends with the given username
	 * 
	 * @param friend
	 * @return true if friends
	 */
	public boolean hasFriend(String friend) throws RemoteException,
			SQLException {
		String[] userFriends = getFriends(getUsername());
		for (int i = 0; i < userFriends.length; i++) {
			if (userFriends[i].equals(friend)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a friend from a players list of friends.
	 * 
	 * @param friend
	 * @return
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public boolean deleteFriend(String friend) throws RemoteException,
			SQLException {
		UserObject friendObject = currentUserClient.getUserProxy()
				.getUserObject(friend); // need getUserObject from username
		int i = currentUserClient.getUserProxy().deleteFriend(userId,
				friendObject.getId());
		System.out.println(currentUser.getName() + " wants to add " + friend
				+ " as a friend");
		if (i == 0) {
			return true;
		}
		return false;

	}

	/**
	 * TODO: Implement Server methods for AVATAR to implement this
	 * 
	 * @param avatar
	 * @return
	 * @author Peter
	 */
	public static boolean setAvatar(ImageIcon avatar) {

		// Sets a users avatar

		return true;
	}

	/**
	 * Gets the avatar for the user TODO: Need server implementation to complete
	 * method
	 * 
	 * @param username
	 * @author Peter
	 */
	public static ImageIcon getAvatar(String username) {
		java.net.URL path = GUIClient.class
				.getResource("images/question_mark.jpg");
		ImageIcon avatar = new ImageIcon(path);

		// Returns the avatar for a particular user

		return avatar;
	}

	/**
	 * Sets the avatar icon for the current user.
	 * 
	 * @param avatar
	 * @return completed
	 */
	public static boolean setAvatarIcon(ImageIcon avatar) {

		return true;
	}

	/**
	 * Gets the 50 x 50 avatar icon for a particular user.
	 */
	public static ImageIcon getAvatarIcon(String username) {
		ImageIcon avatarIcon = new ImageIcon(
				GUIClient.class.getResource("images/person_icon.png"));

		/*
		 * BufferedImage image = null; try { image =
		 * ImageIO.read(GUIClient.class
		 * .getResource("/icons/smiley.jpg").toString()); } catch (IOException
		 * ex) { System.out.println("Image IO read failed"); }
		 */
		// BufferedImage bufferedImage = ImageResizer.resizeTrick(image, width,
		// height);

		// ImageIcon avatar =
		// ImageResizer.resizeImage(GUIClient.class.getResource("/icons/smiley.jpg").toString(),
		// 50, 50);
		// opponent1AvatarLabel.setIcon(avatar);

		return avatarIcon;
	}

	/**
	 * Registers a user on the database with the given information
	 * 
	 * @param accountInfo
	 * @return: true if successful, false if not
	 * @author Peter
	 */
	public boolean registerNewAccount(String[] accountInfo)
			throws RemoteException, SQLException {
		UserObject newUser = new UserObject(accountInfo[2], accountInfo[4],
				accountInfo[3], 500);
		// automatically gives the player 500 chips for signing up
		int i = currentUserClient.getUserProxy().signup(newUser);
		if (i == 0) {
			System.out
					.println("Account information sent to server, you are now registered.");
			return true;
		}
		return false;
	}

	/**
	 * This logs the user in if the email password pair is correct, sets the
	 * current user to the logged in user
	 * 
	 * @param email
	 * @param password
	 * @return: true if successful, false if not
	 * @author Peter
	 */
	public boolean testEmailPassword(String email, String password)
			throws RemoteException, SQLException {

		this.currentUser = currentUserClient.getUserProxy().login(email,
				password);
		if (currentUser != null)
			this.userId = currentUser.getId();
		System.out.println("You are now logged in," + this.currentUser + ".");

		if (this.currentUser != null) {
			return true;
		}

		return false;
	}

	/**
	 * Tells server to send a password recovery email to user.
	 * 
	 * @param email
	 * @author Peter
	 */
	public boolean sendPasswordRecoveryEmail(String email)
			throws RemoteException, SQLException {

		currentUserClient.getUserProxy().recoverPassword(email);
		System.out.println("Password recovery email sent to " + email
				+ "'s email");

		return true;
	}

	// #ENDLOGINANDUSERMETHODS

	// #BEGINGAMELOBBYMETHODS
	/**
	 * This gets the list of GameTableId's from the server and returns it as an
	 * array of Strings
	 * 
	 * @return
	 * @author Peter
	 */
	public String[] getListOfGameTables() {
		String[] gameTables;
		if (currentGameTableClient.getUserProxy().getAllTables() != null) {
			gameTables = new String[currentGameTableClient.getUserProxy()
					.getAllTables().size()];
			int i = 1;
			for (IGameTable element : currentGameTableClient.getUserProxy()
					.getAllTables()) {
				gameTables[i] = element.getName();
				i++;
			}
		} else {
			gameTables = new String[1];
		}

		gameTables[0] = "Default Table";

		// Returns a list of the game tables on the server
		System.out.println("Get list of tables from server");

		return gameTables;
	}

	/**
	 * Returns the host of a particular table.
	 * 
	 * @param table
	 * @return the host of the table
	 */
	public String getTableHost(String table) {
		IGameTable desiredGameTable = currentGameTableClient.getUserProxy()
				.getTable(table);
		String hostname = currentUserClient.getUserProxy().getUserObject(desiredGameTable.getHostId()).getName();
		if (hostname != null)
			return hostname;
		return "You are all the host.";
	}

	/**
	 * This gets the players at a selected table
	 * 
	 * @param table
	 * @return: an array of stings of players in the game
	 * @author Peter
	 */
	public String[] getPlayersAtGameTable(String tableId) {
		IGameTable desiredTable = currentGameTableClient.getUserProxy().getTable(
				tableId);
		ArrayList<Player> players = desiredTable.getPlayers();
		String[] listOfPlayers = { "Empty", "Empty", "Empty", "Empty", "Empty" };
		int i = 0;

		for (Player element : players) {
			listOfPlayers[i] = currentUserClient.getUserProxy()
					.getUserObject(element.getUserId()).getName();
			i++;
		}

		// Returns a list of players names at a particular table
		System.out
				.println("Get players at table, " + tableId + ", from server");

		return listOfPlayers;
	}

	/**
	 * Gets only the opponents other than the current user at a table
	 * 
	 * @param username
	 * @param table
	 * @return
	 */
	public String[] getOpponentsAtGameTable(String username, String table) {
		String[] listOfOpponents = { "Empty", "Empty", "Empty", "Empty" };
		String[] listOfPlayers = getPlayersAtGameTable(table);
		int i = 0;
		int j = 0;
		while (i < 5) {
			if (listOfPlayers[i].equals(username)) {
				listOfOpponents[j] = listOfPlayers[i];
				j++;
			}
			i++;
		}
		System.out.println("Get opponents at game table");

		return listOfOpponents;
	}

	/**
	 * Create a new game table on the server.
	 * 
	 * @param newTableInfo
	 *            = {tableNameString, anteString, bringInString}
	 * @return: boolean successful or not
	 */
	public boolean createNewTable(String[] newTableInfo) {
		// String[] createNewTableFields = {tableNameString, anteString,
		// bringInString};
		ArrayList<Integer> playersInGame = new ArrayList<Integer>();
		playersInGame.add(currentUser.getId());
		int ante = java.lang.Integer.parseInt(newTableInfo[0]);
		double bringIn = java.lang.Double.parseDouble(newTableInfo[2]);
		IGameTable desiredTable = currentGameTableClient.getUserProxy().createTable(
				ante, currentUser.getId(), playersInGame, bringIn,
				newTableInfo[0]);
		if (desiredTable != null) {
			this.usersGameTable = desiredTable;
			return true;
		}
		return false;
	}

	/**
	 * This returns the ante for a given table
	 * 
	 * @param table
	 * @return
	 * @author Peter
	 */
	public int getTableAnte(String table) {
		if (currentGameTableClient.getUserProxy().getTable(table) != null)
			return currentGameTableClient.getUserProxy().getTable(table).getAnte();
		return -1;
	}

	/**
	 * Gets the bring in for a particular table.
	 * 
	 * @param table
	 * @return
	 * @author Peter
	 */
	public double getTableBringIn(String table) {
		if (currentGameTableClient.getUserProxy().getTable(table) != null)
			return currentGameTableClient.getUserProxy().getTable(table)
					.getBringIn();
		return -1;
	}

	/**
	 * This allows the player to join a Game Table, adds him to the game table
	 * on the server and updates his status in the Game Table
	 * 
	 * @param username
	 * @param table
	 * @return
	 * @author Peter
	 */
	public boolean joinGameTable(String table) {
		int i = currentGameTableClient.getUserProxy().getTable(table)
				.addPlayer(userId);
		this.usersGameTable = currentGameTableClient.getUserProxy().getTable(table);
		if (i == 0 && this.usersGameTable != null)
			return true;
		return false;
	}

	/**
	 * Return the current table a user is apart of.
	 * 
	 * @return table name string
	 */
	public String getCurrentTable() {
		if (this.usersGameTable != null)
			return usersGameTable.getName();
		return "Sorry you're not part of a table.";
	}

	/**
	 * Tells the server that a user has left the table
	 * 
	 * @param table
	 * @return completed
	 */
	public boolean leaveGameTable(String table) {
		if (usersGameTable != null) {
			currentGameTableClient.getUserProxy().getTable(table)
					.removePlayer(userId);
			usersGameTable = null;
			return true;
		}

		return false;
	}

	/**
	 * Method called if a host wishes to start the game.
	 * 
	 * @param table
	 * @return request answer
	 */
	public boolean startGameRequest(String table) {
		if (usersGameTable != null && usersGameTable.getHostId() == userId) {
			currentGameTableClient.getUserProxy().getTable(table).initiateGame();
			currentPlayerClient = new PlayerClient(userId, currentGameTableClient
					.getUserProxy().getTable(table).getGame().getId());
			currentGameTableClient.getUserProxy().getTable(table).startGame();
			return true;
		}

		return false;
	}

	/**
	 * Sends a table invite to the server.
	 * 
	 * @param table
	 * @param friend
	 * @return completed
	 */
	public static boolean inviteFriendToTable(String table, String friend) {

		return true;
	}

	// #ENDGAMELOBBYMETHODS
	// #BEGINGAMEMETHODS

	/**
	 * This gets the leader board from the server
	 * 
	 * @return
	 */
	public static String[][] retrieveStatistics() throws SQLException {
		return Server.statisticsModule.leaderBoard.leaderBoardDisplay();
	}

	public boolean sendBet(double bet) {
		return true;
	}

	public boolean fold() {
		return true;
	}

	public boolean allIn() {
		return true;
	}

	public boolean call() {
		return true;
	}

	/**
	 * Gets chips for a particular user.
	 */
	public String getChips(String username) {
		UserObject desiredUser = currentUserClient.getUserProxy()
				.getUserObject(username);
		String chips = "" + desiredUser.getChips();
		return chips;
		// This is in Game.... Change.....
	}

	/**
	 * Gets the running pot of a table.
	 */
	public String getPot(String table) {

    	
        return "8675309";
    }

	/**
	 * Returns the current mimimum bet at a table.
	 */
	public String getMinimumBet(String table) {

		return "15";
	}

	public String[] getPlayerCards() {
		// "clubs-5-150.png"
		// return String array of the number of cards that I have
	}

	public String[] getOpponentCards(String username) {

	}

	public boolean leaveGame() {

	}

	// #ENDGAMEMETHODS
	// #BEGINCHATMETHODS
	/**
	 * Sends a chat message to server.
	 */
	public static boolean sendChatMessage(String usernameSender,
			String[] usernameRecipients, String message) {

		System.out.println("String message sent to server");

		return true;
	}

	// #ENDCHATMETHODS
	// #BEGINCONNECTIONMETHODS
	/**
	 * Setter for IP.
	 */
	public static void setIp(String ip) {
		IP = ip;
	}

	/**
	 * Getter for IP.
	 */
	public static String getIp() {
		return IP;
	}

	/**
	 * Method pings server to check connection.
	 */
	public static boolean ping() {

		// Pings server to test connection

		return false;
	}
	// End Connection Methods
}
