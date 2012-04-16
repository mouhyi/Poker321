package GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Remote.IPlayer;

/**
 * The Game Frame class extends JFrame to illustrate a game of five card stud.
 * 
 * @author mattvertescher
 */
public class GameFrame extends javax.swing.JFrame {

	public static GUIClient clientRequest;
	public static ServerListener serverListener;

	private String TABLE;
	private int ANTE;

	private boolean yourTurn = false;

	/**
	 * Creates new form GameScreen.
	 */
	public GameFrame(GUIClient guic, ServerListener sl, String tableName)
			throws RemoteException, SQLException {
		initComponents();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((screenSize.width / 2) - (this.getWidth() / 2),
				(screenSize.height / 2) - (this.getHeight() / 2));
		this.setIconImage(new ImageIcon(GUIClient.class
				.getResource("images/icon_playing_card.png")).getImage());

		this.setVisible(true);

		clientRequest = guic;
		serverListener = sl;
		serverListener.setGameFrame(this);
		System.out.println("Game frame init");

		TABLE = tableName;
		ANTE = clientRequest.getTableAnte(TABLE);

		String[] listOfOpponents = clientRequest.getOpponentsInGame(
				clientRequest.getUsername(), TABLE);

		playerNameLabel.setText(clientRequest.getUsername());
		if (listOfOpponents.length >= 1) {
			opponent1NameLabel.setText(listOfOpponents[0]);
			opponent1ChipsLabel.setText(clientRequest
					.getUsersWorth(listOfOpponents[0]));
		}
		if (listOfOpponents.length >= 2) {
			opponent2NameLabel.setText(listOfOpponents[1]);
			opponent2ChipsLabel.setText(clientRequest
					.getUsersWorth(listOfOpponents[1]));
		}
		if (listOfOpponents.length >= 3) {
			opponent3NameLabel.setText(listOfOpponents[2]);
			opponent3ChipsLabel.setText(clientRequest
					.getUsersWorth(listOfOpponents[2]));
		}
		if (listOfOpponents.length == 4) {
			opponent4NameLabel.setText(listOfOpponents[3]);
			opponent4ChipsLabel.setText(clientRequest
					.getUsersWorth(listOfOpponents[3]));
		}

		playerAvatarLabel.setIcon(GUIClient.getAvatarIcon(playerNameLabel
				.getText()));
		opponent1AvatarLabel.setIcon(GUIClient.getAvatarIcon(opponent1NameLabel
				.getText()));
		opponent2AvatarLabel.setIcon(GUIClient.getAvatarIcon(opponent2NameLabel
				.getText()));
		opponent3AvatarLabel.setIcon(GUIClient.getAvatarIcon(opponent3NameLabel
				.getText()));
		opponent4AvatarLabel.setIcon(GUIClient.getAvatarIcon(opponent4NameLabel
				.getText()));

		clearAllCardIcons();

		gameConsoleTextArea
				.append("  Welcome to Five Card Stud\n\n  Please wait until the game starts.\n");

		enableBettingInputFields(false);

		clientRequest.clientGameFrameDoneInitializing();
	}

	// Logic Methods

	/**
	 * Clear the game panel of card icons.
	 */
	private void clearAllCardIcons() {
		String[] listOfOpponents = clientRequest.getOpponentsInGame(
				clientRequest.getUsername(), TABLE);
		clearCardIconsForUser(clientRequest.getUsername());
		for (int i = 0; i < listOfOpponents.length; i++) {
			if (!listOfOpponents[i].equals("Empty"))
				clearCardIconsForUser(listOfOpponents[i]);
		}
	}

	/**
	 * Clear a users card icons.
	 * 
	 * @param username
	 */
	private void clearCardIconsForUser(String username) {
		ImageIcon emptyIcon = new ImageIcon("");
		ImageIcon[] emptyIconSet = { emptyIcon, emptyIcon, emptyIcon,
				emptyIcon, emptyIcon };
		setCardIconsForUser(username, emptyIconSet);
	}

	/**
	 * Updates the cards for a particular user.
	 * 
	 * @param username
	 * @param cards
	 * @return completed
	 */
	private boolean setCardIconsForUser(String username, ImageIcon[] cards) {
		if (username.equals(playerNameLabel.getText())) {
			playerCardLabel1.setIcon(cards[0]);
			playerCardLabel2.setIcon(cards[1]);
			playerCardLabel3.setIcon(cards[2]);
			playerCardLabel4.setIcon(cards[3]);
			playerCardLabel5.setIcon(cards[4]);
		}

		else if (username.equals(opponent1NameLabel.getText())) {
			opponent1CardLabel1.setIcon(cards[0]);
			opponent1CardLabel2.setIcon(cards[1]);
			opponent1CardLabel3.setIcon(cards[2]);
			opponent1CardLabel4.setIcon(cards[3]);
			opponent1CardLabel5.setIcon(cards[4]);
		}

		else if (username.equals(opponent2NameLabel.getText())) {
			opponent2CardLabel1.setIcon(cards[0]);
			opponent2CardLabel2.setIcon(cards[1]);
			opponent2CardLabel3.setIcon(cards[2]);
			opponent2CardLabel4.setIcon(cards[3]);
			opponent2CardLabel5.setIcon(cards[4]);
		}

		else if (username.equals(opponent3NameLabel.getText())) {
			opponent3CardLabel1.setIcon(cards[0]);
			opponent3CardLabel2.setIcon(cards[1]);
			opponent3CardLabel3.setIcon(cards[2]);
			opponent3CardLabel4.setIcon(cards[3]);
			opponent3CardLabel5.setIcon(cards[4]);
		}

		else if (username.equals(opponent4NameLabel.getText())) {
			opponent4CardLabel1.setIcon(cards[0]);
			opponent4CardLabel2.setIcon(cards[1]);
			opponent4CardLabel3.setIcon(cards[2]);
			opponent4CardLabel4.setIcon(cards[3]);
			opponent4CardLabel5.setIcon(cards[4]);
		}
		return true;
	}

	/**
	 * Method to be called by the server telling the GameFrame to update a users
	 * cards.
	 * 
	 * @param username
	 * @return completed
	 */
	public boolean updateCardsForUser(String username) {
		String[] iconPaths = null;
		if (username.equals(clientRequest.getUsername())) {
			System.out.println("gets player cards for username game frame");
			iconPaths = clientRequest.getPlayerCards();
		} else if (!username.equals(clientRequest.getUsername())
				&& !username.equals("Empty") && username != null) {
			iconPaths = clientRequest.getOpponentCards(username);
		}

		if (iconPaths == null) {
			// clearCardIconsForUser(username);
			System.out.println("iconpaths is null in game fram");
			return true;
		}

		int numberOfCards = iconPaths.length;

		ImageIcon emptyIcon = new ImageIcon("");
		ImageIcon[] cardIcons = { emptyIcon, emptyIcon, emptyIcon, emptyIcon,
				emptyIcon };

		for (int i = 0; i < numberOfCards; i++) {
			// if(!cardIcons[i].equals(emptyIcon))
			try {
				cardIcons[i] = new ImageIcon(GUIClient.class
						.getResource(iconPaths[i]));
			} catch (NullPointerException e) {
				System.out.println("NULL FORMAT");
			}
		}
		System.out.println("finishing game frame");
		return setCardIconsForUser(username, cardIcons);
	}

	public boolean updateAllCards() {
		String[] usersInGame = clientRequest.getPlayersInGame(TABLE);
		int[] foldedPlayers = clientRequest.getFoldedPlayers(TABLE);
		if (foldedPlayers != null) {
			for (int i = 0; i < foldedPlayers.length; i++) {
				this.removeUserFromGame(foldedPlayers[i]);
			}
		}
		for (int i = 0; i < usersInGame.length; i++) {
			if (!usersInGame.equals("Empty"))
				updateCardsForUser(usersInGame[i]);
		}
		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Updates the betting fields across the game panel.
	 */
	public boolean updateBettingSystem() {
		String[] listOfOpponents = clientRequest.getOpponentsAtGameTable(
				clientRequest.getUsername(), TABLE);
		for (int i = 0; i < listOfOpponents.length; i++)
			updateChipsForUser(listOfOpponents[i]);
		updateChipsForUser(clientRequest.getUsername());
		updatePot();
		updateCurrentBet();

		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Changes the username label for a particular user.
	 * 
	 * @param oldUsername
	 * @param newUsername
	 * @return
	 */
	public boolean setUsernameLabelForUser(String oldUsername,
			String newUsername) {
		if (oldUsername.equals(playerNameLabel.getText()))
			playerNameLabel.setText(newUsername);

		else if (oldUsername.equals(opponent1NameLabel.getText()))
			opponent1NameLabel.setText(newUsername);

		else if (oldUsername.equals(opponent2NameLabel.getText()))
			opponent2NameLabel.setText(newUsername);

		else if (oldUsername.equals(opponent3NameLabel.getText()))
			opponent3NameLabel.setText(newUsername);

		else if (oldUsername.equals(opponent4NameLabel.getText()))
			opponent4NameLabel.setText(newUsername);

		return true;
	}

	/**
	 * Updates the pot label.
	 * 
	 * @return
	 */
	public boolean updatePot() {
		gamePotLabel.setText("$" + clientRequest.getPot(TABLE));
		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Updates the current bet label.
	 * 
	 * @return
	 */
	public boolean updateCurrentBet() {
		currentBetLabel.setText("Current minimum bet: "
				+ clientRequest.getMinimumBet(TABLE));
		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Changes the chips label for a particular user.
	 * 
	 * @param username
	 * @param chips
	 * @return
	 */
	public boolean updateChipsForUser(String username) {
		if (username.equals("Empty"))
			return true;
		if (username.equals(playerNameLabel.getText()))
			playerChipsLabel.setText("$" + clientRequest.getChips(username));

		else if (username.equals(opponent1NameLabel.getText()))
			opponent1ChipsLabel.setText("$" + clientRequest.getChips(username));

		else if (username.equals(opponent2NameLabel.getText()))
			opponent2ChipsLabel.setText("$" + clientRequest.getChips(username));

		else if (username.equals(opponent3NameLabel.getText()))
			opponent3ChipsLabel.setText("$" + clientRequest.getChips(username));

		else if (username.equals(opponent4NameLabel.getText()))
			opponent4ChipsLabel.setText("$" + clientRequest.getChips(username));

		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Changes the avatar label for a particular user.
	 * 
	 * @param username
	 * @param avatar
	 * @return
	 */
	public boolean setAvatarIconForUser(String username, ImageIcon avatar) {
		if (username.equals(playerNameLabel.getText()))
			playerAvatarLabel.setIcon(avatar);

		else if (username.equals(opponent1NameLabel.getText()))
			opponent1AvatarLabel.setIcon(avatar);

		else if (username.equals(opponent2NameLabel.getText()))
			opponent2AvatarLabel.setIcon(avatar);

		else if (username.equals(opponent3NameLabel.getText()))
			opponent3AvatarLabel.setIcon(avatar);

		else if (username.equals(opponent4NameLabel.getText()))
			opponent4AvatarLabel.setIcon(avatar);

		return true;
	}

	/**
	 * Removes a users icons from the game.
	 * 
	 * @param username
	 * @return
	 */
	public boolean removeUserFromGame(int uID) {
		String username = null;
		try {
			username = clientRequest.getCurrentUserClient().getUserProxy()
					.getUserObject(uID).getName();

		} catch (RemoteException e) {
		} catch (SQLException e) {
		}
		System.out.println("GAME FRAME: REMOVED USER FROM GAME:" + username);
		clearCardIconsForUser(username);
		// setAvatarIconForUser(username, new ImageIcon(""));

		// addMessageToInGameConsole(username + " has left the game");

		return clientRequest.clientNeedsGameFrameUpdate();
	}

	public boolean startTurn() {
		yourTurn = true;
		enableBettingInputFields(yourTurn);
		playerBetTextField.setText(clientRequest.getMinimumBet(TABLE));
		return clientRequest.clientNeedsGameFrameUpdate();
	}

	public boolean endTurn() {
		yourTurn = false;
		enableBettingInputFields(yourTurn);
		return true;
	}

	public boolean resetGame() {
		clearAllCardIcons();
		updateBettingSystem();

		return clientRequest.clientNeedsGameFrameUpdate();
	}

	/**
	 * Returns to the main menu screen.
	 */
	public boolean exitGame() throws RemoteException, SQLException {
		clientRequest.leaveGame();
		MainMenuFrame mm = new MainMenuFrame(clientRequest, serverListener);
		mm.setVisible(true);
		serverListener.releaseGameFrame();
		this.setVisible(false);
		this.dispose();
		return true;
	}

	/**
	 * Updates the game console with a list of new messages.
	 * 
	 * @param newMessages
	 */
	public boolean addMessageToInGameConsole(String message) {
		// for (int i = 0; i < newMessages.length; i++)
		// gameConsoleTextArea.append("  " + newMessages[i] + "\n");

		gameConsoleTextArea.append("  " + message + "\n");

		// TO BE FIXED
		Rectangle visableRect = gameConsoleTextArea.getVisibleRect();
		visableRect.y = gameConsoleTextArea.getHeight() - visableRect.height;
		gameConsoleTextArea.scrollRectToVisible(visableRect);

		return clientRequest.clientNeedsGameFrameUpdate();
	}

	private void enableBettingInputFields(boolean enabled) {
		// playerBetTextField.enableInputMethods(enabled);
		// betCheckButton.enableInputMethods(enabled);
		// foldButton.enableInputMethods(enabled);
		playerBetTextField.setEnabled(enabled);
		betCheckButton.setEnabled(enabled);
		foldButton.setEnabled(enabled);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		mainLayeredPane = new javax.swing.JLayeredPane();
		playerCardsLayeredPane = new javax.swing.JLayeredPane();
		playerCardLabel1 = new javax.swing.JLabel();
		playerCardLabel2 = new javax.swing.JLabel();
		playerCardLabel3 = new javax.swing.JLabel();
		playerCardLabel4 = new javax.swing.JLabel();
		playerCardLabel5 = new javax.swing.JLabel();
		playerInfoLayeredPane = new javax.swing.JLayeredPane();
		playerAvatarLabel = new javax.swing.JLabel();
		playerNameLabel = new javax.swing.JLabel();
		playerBettingLayeredPane = new javax.swing.JLayeredPane();
		playerChipsLabel = new javax.swing.JLabel();
		currentBetLabel = new javax.swing.JLabel();
		playerBetTextField = new javax.swing.JTextField();
		betCheckButton = new javax.swing.JButton();
		foldButton = new javax.swing.JButton();
		opponent1LayeredPane = new javax.swing.JLayeredPane();
		opponent1AvatarLabel = new javax.swing.JLabel();
		opponent1CardLabel1 = new javax.swing.JLabel();
		opponent1CardLabel2 = new javax.swing.JLabel();
		opponent1CardLabel3 = new javax.swing.JLabel();
		opponent1CardLabel4 = new javax.swing.JLabel();
		opponent1CardLabel5 = new javax.swing.JLabel();
		opponent1NameLabel = new javax.swing.JLabel();
		opponent1ChipsLabel = new javax.swing.JLabel();
		opponent2LayeredPane = new javax.swing.JLayeredPane();
		opponent2AvatarLabel = new javax.swing.JLabel();
		opponent2CardLabel1 = new javax.swing.JLabel();
		opponent2CardLabel2 = new javax.swing.JLabel();
		opponent2CardLabel3 = new javax.swing.JLabel();
		opponent2CardLabel4 = new javax.swing.JLabel();
		opponent2CardLabel5 = new javax.swing.JLabel();
		opponent2NameLabel = new javax.swing.JLabel();
		opponent2ChipsLabel = new javax.swing.JLabel();
		opponent3LayeredPane = new javax.swing.JLayeredPane();
		opponent3AvatarLabel = new javax.swing.JLabel();
		opponent3CardLabel1 = new javax.swing.JLabel();
		opponent3CardLabel2 = new javax.swing.JLabel();
		opponent3CardLabel3 = new javax.swing.JLabel();
		opponent3CardLabel4 = new javax.swing.JLabel();
		opponent3CardLabel5 = new javax.swing.JLabel();
		opponent3NameLabel = new javax.swing.JLabel();
		opponent3ChipsLabel = new javax.swing.JLabel();
		opponent4LayeredPane = new javax.swing.JLayeredPane();
		opponent4AvatarLabel = new javax.swing.JLabel();
		opponent4CardLabel1 = new javax.swing.JLabel();
		opponent4CardLabel2 = new javax.swing.JLabel();
		opponent4CardLabel3 = new javax.swing.JLabel();
		opponent4CardLabel4 = new javax.swing.JLabel();
		opponent4CardLabel5 = new javax.swing.JLabel();
		opponent4NameLabel = new javax.swing.JLabel();
		opponent4ChipsLabel = new javax.swing.JLabel();
		gamePotLayeredPane = new javax.swing.JLayeredPane();
		gamePotLabel = new javax.swing.JLabel();
		gameConsoleScrollPane = new javax.swing.JScrollPane();
		gameConsoleTextArea = new javax.swing.JTextArea();
		backgroundLabel = new javax.swing.JLabel();
		menuBar = new javax.swing.JMenuBar();
		fiveCardStudMenu = new javax.swing.JMenu();
		exitGameMenuItem = new javax.swing.JMenuItem();
		quitFiveCardStudMenuItem = new javax.swing.JMenuItem();
		viewMenu = new javax.swing.JMenu();
		openChatMenuItem = new javax.swing.JMenuItem();
		openStatisticsMenuItem = new javax.swing.JMenuItem();
		helpMenu = new javax.swing.JMenu();
		gameRulesMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Five Card Stud");
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});

		mainLayeredPane.setBackground(new java.awt.Color(0, 51, 51));

		playerCardLabel1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards150px/back-blue-150-1.png"))); // NOI18N
		playerCardLabel1.setBounds(170, 10, 150, 215);
		playerCardsLayeredPane.add(playerCardLabel1,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerCardLabel2.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards150px/back-blue-150-1.png"))); // NOI18N
		playerCardLabel2.setSize(new java.awt.Dimension(150, 215));
		playerCardLabel2.setBounds(130, 10, 150, 215);
		playerCardsLayeredPane.add(playerCardLabel2,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerCardLabel3.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards150px/back-blue-150-1.png"))); // NOI18N
		playerCardLabel3.setBounds(90, 10, 150, 215);
		playerCardsLayeredPane.add(playerCardLabel3,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerCardLabel4.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards150px/back-blue-150-1.png"))); // NOI18N
		playerCardLabel4.setBounds(50, 10, 150, 215);
		playerCardsLayeredPane.add(playerCardLabel4,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerCardLabel5.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards150px/back-blue-150-1.png"))); // NOI18N
		playerCardLabel5.setBounds(10, 10, 150, 215);
		playerCardsLayeredPane.add(playerCardLabel5,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerCardsLayeredPane.setBounds(330, 420, 330, 230);
		mainLayeredPane.add(playerCardsLayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerAvatarLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		playerAvatarLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/person_icon.png"))); // NOI18N
		playerAvatarLabel.setBounds(0, 40, 130, 50);
		playerInfoLayeredPane.add(playerAvatarLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerNameLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		playerNameLabel.setText("Player Name");
		playerNameLabel.setBounds(0, 120, 130, 16);
		playerInfoLayeredPane.add(playerNameLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerInfoLayeredPane.setBounds(180, 450, 130, 180);
		mainLayeredPane.add(playerInfoLayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerBettingLayeredPane
				.setBackground(new java.awt.Color(255, 255, 255));
		playerBettingLayeredPane.setBorder(javax.swing.BorderFactory
				.createEtchedBorder());

		playerChipsLabel.setText("Chips:");
		playerChipsLabel.setBounds(10, 10, 180, 16);
		playerBettingLayeredPane.add(playerChipsLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		currentBetLabel.setText("Current Bet:");
		currentBetLabel.setBounds(10, 40, 180, 16);
		playerBettingLayeredPane.add(currentBetLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);
		playerBetTextField.setBounds(10, 70, 180, 28);
		playerBettingLayeredPane.add(playerBetTextField,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		betCheckButton.setText("Bet / Check");
		betCheckButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				betCheckButtonActionPerformed(evt);
			}
		});
		betCheckButton.setBounds(10, 110, 180, 29);
		playerBettingLayeredPane.add(betCheckButton,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		foldButton.setText("Fold");
		foldButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				foldButtonActionPerformed(evt);
			}
		});
		foldButton.setBounds(10, 150, 180, 29);
		playerBettingLayeredPane.add(foldButton,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		playerBettingLayeredPane.setBounds(680, 440, 200, 190);
		mainLayeredPane.add(playerBettingLayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1AvatarLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent1AvatarLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/person_icon.png"))); // NOI18N
		opponent1AvatarLabel.setSize(new java.awt.Dimension(50, 50));
		opponent1AvatarLabel.setBounds(20, 20, 50, 50);
		opponent1LayeredPane.add(opponent1AvatarLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1CardLabel1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent1CardLabel1.setBounds(140, 80, 75, 107);
		opponent1LayeredPane.add(opponent1CardLabel1,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1CardLabel2.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent1CardLabel2.setBounds(110, 80, 75, 107);
		opponent1LayeredPane.add(opponent1CardLabel2,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1CardLabel3.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent1CardLabel3.setBounds(80, 80, 75, 107);
		opponent1LayeredPane.add(opponent1CardLabel3,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1CardLabel4.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent1CardLabel4.setBounds(50, 80, 75, 107);
		opponent1LayeredPane.add(opponent1CardLabel4,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1CardLabel5.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent1CardLabel5.setBounds(20, 80, 75, 107);
		opponent1LayeredPane.add(opponent1CardLabel5,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1NameLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent1NameLabel.setText("Opponent 1 Name");
		opponent1NameLabel.setBounds(90, 20, 130, 16);
		opponent1LayeredPane.add(opponent1NameLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1ChipsLabel.setText("Chips");
		opponent1ChipsLabel.setBounds(90, 50, 120, 16);
		opponent1LayeredPane.add(opponent1ChipsLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent1LayeredPane.setBounds(20, 210, 230, 200);
		mainLayeredPane.add(opponent1LayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2AvatarLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent2AvatarLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/person_icon.png"))); // NOI18N
		opponent2AvatarLabel.setSize(new java.awt.Dimension(50, 50));
		opponent2AvatarLabel.setBounds(20, 20, 50, 50);
		opponent2LayeredPane.add(opponent2AvatarLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2CardLabel1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent2CardLabel1.setBounds(140, 80, 75, 107);
		opponent2LayeredPane.add(opponent2CardLabel1,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2CardLabel2.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent2CardLabel2.setBounds(110, 80, 75, 107);
		opponent2LayeredPane.add(opponent2CardLabel2,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2CardLabel3.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent2CardLabel3.setBounds(80, 80, 75, 107);
		opponent2LayeredPane.add(opponent2CardLabel3,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2CardLabel4.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent2CardLabel4.setBounds(50, 80, 75, 107);
		opponent2LayeredPane.add(opponent2CardLabel4,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2CardLabel5.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent2CardLabel5.setBounds(20, 80, 75, 107);
		opponent2LayeredPane.add(opponent2CardLabel5,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2NameLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent2NameLabel.setText("Opponent 2 Name");
		opponent2NameLabel.setBounds(90, 20, 130, 16);
		opponent2LayeredPane.add(opponent2NameLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2ChipsLabel.setText("Chips");
		opponent2ChipsLabel.setBounds(90, 50, 120, 16);
		opponent2LayeredPane.add(opponent2ChipsLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent2LayeredPane.setBounds(250, 10, 230, 200);
		mainLayeredPane.add(opponent2LayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3AvatarLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent3AvatarLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/person_icon.png"))); // NOI18N
		opponent3AvatarLabel.setSize(new java.awt.Dimension(50, 50));
		opponent3AvatarLabel.setBounds(20, 20, 50, 50);
		opponent3LayeredPane.add(opponent3AvatarLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3CardLabel1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent3CardLabel1.setBounds(140, 80, 75, 107);
		opponent3LayeredPane.add(opponent3CardLabel1,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3CardLabel2.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent3CardLabel2.setBounds(110, 80, 75, 107);
		opponent3LayeredPane.add(opponent3CardLabel2,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3CardLabel3.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent3CardLabel3.setBounds(80, 80, 75, 107);
		opponent3LayeredPane.add(opponent3CardLabel3,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3CardLabel4.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent3CardLabel4.setBounds(50, 80, 75, 107);
		opponent3LayeredPane.add(opponent3CardLabel4,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3CardLabel5.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent3CardLabel5.setBounds(20, 80, 75, 107);
		opponent3LayeredPane.add(opponent3CardLabel5,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3NameLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent3NameLabel.setText("Opponent 3 Name");
		opponent3NameLabel.setBounds(90, 20, 130, 16);
		opponent3LayeredPane.add(opponent3NameLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3ChipsLabel.setText("Chips");
		opponent3ChipsLabel.setBounds(90, 50, 120, 16);
		opponent3LayeredPane.add(opponent3ChipsLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent3LayeredPane.setBounds(490, 10, 230, 200);
		mainLayeredPane.add(opponent3LayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4AvatarLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent4AvatarLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/person_icon.png"))); // NOI18N
		opponent4AvatarLabel.setSize(new java.awt.Dimension(50, 50));
		opponent4AvatarLabel.setBounds(20, 20, 50, 50);
		opponent4LayeredPane.add(opponent4AvatarLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4CardLabel1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent4CardLabel1.setBounds(140, 80, 75, 107);
		opponent4LayeredPane.add(opponent4CardLabel1,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4CardLabel2.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent4CardLabel2.setBounds(110, 80, 75, 107);
		opponent4LayeredPane.add(opponent4CardLabel2,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4CardLabel3.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent4CardLabel3.setBounds(80, 80, 75, 107);
		opponent4LayeredPane.add(opponent4CardLabel3,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4CardLabel4.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent4CardLabel4.setBounds(50, 80, 75, 107);
		opponent4LayeredPane.add(opponent4CardLabel4,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4CardLabel5.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/cards75px/back-blue-75-1.png"))); // NOI18N
		opponent4CardLabel5.setBounds(20, 80, 75, 107);
		opponent4LayeredPane.add(opponent4CardLabel5,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4NameLabel.setBackground(new java.awt.Color(255, 255, 255));
		opponent4NameLabel.setText("Opponent 4 Name");
		opponent4NameLabel.setBounds(90, 20, 130, 16);
		opponent4LayeredPane.add(opponent4NameLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4ChipsLabel.setText("Chips");
		opponent4ChipsLabel.setBounds(90, 50, 120, 16);
		opponent4LayeredPane.add(opponent4ChipsLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		opponent4LayeredPane.setBounds(740, 210, 230, 200);
		mainLayeredPane.add(opponent4LayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		gamePotLabel.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
		gamePotLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		gamePotLabel.setText("$ 0");
		gamePotLabel.setBounds(0, 0, 390, 60);
		gamePotLayeredPane.add(gamePotLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		gameConsoleTextArea.setColumns(20);
		gameConsoleTextArea.setEditable(false);
		gameConsoleTextArea.setRows(5);
		gameConsoleScrollPane.setViewportView(gameConsoleTextArea);

		gameConsoleScrollPane.setBounds(0, 70, 390, 80);
		gamePotLayeredPane.add(gameConsoleScrollPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		gamePotLayeredPane.setBounds(300, 240, 390, 150);
		mainLayeredPane.add(gamePotLayeredPane,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/GUI/images/background.jpg"))); // NOI18N
		backgroundLabel.setBounds(0, 0, 1000, 700);
		mainLayeredPane.add(backgroundLabel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);

		menuBar.setBackground(new java.awt.Color(51, 51, 51));

		fiveCardStudMenu.setText("Five Card Stud");

		exitGameMenuItem.setText("Exit Game");
		exitGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitGameMenuItemActionPerformed(evt);
			}
		});
		fiveCardStudMenu.add(exitGameMenuItem);

		quitFiveCardStudMenuItem.setText("Quit Five Card Stud");
		quitFiveCardStudMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						quitFiveCardStudMenuItemActionPerformed(evt);
					}
				});
		fiveCardStudMenu.add(quitFiveCardStudMenuItem);

		menuBar.add(fiveCardStudMenu);

		viewMenu.setText("View");

		openChatMenuItem.setText("Open Chat");
		openChatMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openChatMenuItemActionPerformed(evt);
			}
		});
		viewMenu.add(openChatMenuItem);

		openStatisticsMenuItem.setText("Open Statistics");
		openStatisticsMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						openStatisticsMenuItemActionPerformed(evt);
					}
				});
		viewMenu.add(openStatisticsMenuItem);

		menuBar.add(viewMenu);

		helpMenu.setText("Help");

		gameRulesMenuItem.setText("Game Rules");
		gameRulesMenuItem
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						gameRulesMenuItemActionPerformed(evt);
					}
				});
		helpMenu.add(gameRulesMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(mainLayeredPane,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1000,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(mainLayeredPane,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 678,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Component Methods

	/**
	 * When chat menu item is clicked, a new chat window is opened.
	 * 
	 * @param evt
	 */
	private void openChatMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_openChatMenuItemActionPerformed
		ChatFrame chatScreen = null;
		try {
			chatScreen = new ChatFrame(clientRequest, serverListener,
					clientRequest.getOpponentsAtGameTable(clientRequest
							.getUsername(), TABLE));
		} catch (RemoteException ex) {
		} catch (SQLException ex) {
		}
		chatScreen.setDefaultCloseOperation(ChatFrame.DISPOSE_ON_CLOSE);
		chatScreen.setVisible(true);

	}// GEN-LAST:event_openChatMenuItemActionPerformed

	// NOT TO BE INCLUDED
	private void openStatisticsMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_openStatisticsMenuItemActionPerformed

		JOptionPane.showMessageDialog(this, "In-Game Statistics Offline",
				"Error", JOptionPane.ERROR_MESSAGE);
		/*
		 * JFrame statisticsFrame = new JFrame(); statisticsFrame.add(new
		 * StatisticsPanel()); statisticsFrame.transferFocus();
		 * statisticsFrame.setVisible(true);
		 * statisticsFrame.setDefaultCloseOperation
		 * (statisticsFrame.DISPOSE_ON_CLOSE);
		 */
	}// GEN-LAST:event_openStatisticsMenuItemActionPerformed

	/**
	 * When the GameFrame window closes, the MainMenuFrame opens and the
	 * GameFrame closes.
	 * 
	 * @param evt
	 */
	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		try {
			exitGame();
		} catch (RemoteException ex) {
		} catch (SQLException ex) {
		}
	}// GEN-LAST:event_formWindowClosing

	/**
	 * When chosen, the MainMenuFrame opens and the GameFrame closes.
	 * 
	 * @param evt
	 */
	private void exitGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitGameMenuItemActionPerformed
		try {
			exitGame();
		} catch (RemoteException ex) {
		} catch (SQLException ex) {
		}
	}// GEN-LAST:event_exitGameMenuItemActionPerformed

	/**
	 * When chosen, the application closes.
	 * 
	 * @param evt
	 */
	private void quitFiveCardStudMenuItemActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_quitFiveCardStudMenuItemActionPerformed
		serverListener.releaseGameFrame();
		this.setVisible(false);
		this.dispose();

		try {
			clientRequest.logout();
		} catch (RemoteException ex) {
		} catch (SQLException ex) {
		}
		System.exit(0);
	}// GEN-LAST:event_quitFiveCardStudMenuItemActionPerformed

	/**
	 * When chosen, the game rules open.
	 * 
	 * @param evt
	 */
	private void gameRulesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_gameRulesMenuItemActionPerformed
		JOptionPane.showMessageDialog(this, "Game Rules Missing", "Error",
				JOptionPane.ERROR_MESSAGE);
	}// GEN-LAST:event_gameRulesMenuItemActionPerformed
	
	/**
	 * When the bet button is pressed, the user's bet is sent.
	 * 
	 * @param evt
	 */
	private void betCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_betCheckButtonActionPerformed
		String betString = playerBetTextField.getText();
		boolean isNumber = false;
		try {
			double d = Double.parseDouble(betString);
			isNumber = true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Not a number.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		if (isNumber) {
			double yourBet = Double.parseDouble(betString);
			double yourChips = Double.parseDouble(clientRequest
					.getChips(clientRequest.getUsername()));
			double minimumBet = Double.parseDouble(clientRequest
					.getMinimumBet(TABLE));

			if (yourBet < yourChips && yourBet < minimumBet) {
				JOptionPane.showMessageDialog(this,
						"Your bet is lower than the minimum bet", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (yourBet > yourChips) {
				JOptionPane.showMessageDialog(this,
						"You don't have those chips", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (yourBet >= minimumBet) {
				endTurn(); // locks buttons
				if (yourBet < yourChips) {
					clientRequest.sendBet(yourBet);
					// addMessageToInGameConsole("You bet " + yourBet);
				}
			} else if (yourBet == yourChips) {
				clientRequest.allIn();
				// addMessageToInGameConsole("You have gone all in " + yourBet);
			}

		}
	}// GEN-LAST:event_betCheckButtonActionPerformed
	
	/**
	 * When the fold button is pressed, a fold message is sent to the server.
	 * 
	 * @param evt
	 */
	private void foldButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_foldButtonActionPerformed
		clientRequest.fold();
		clearCardIconsForUser(clientRequest.getUsername());
		endTurn();
	}// GEN-LAST:event_foldButtonActionPerformed

	/*
	 * public static void main(String args[]) {
	 * 
	 * Sets the JTattoo look and feel
	 * 
	 * try { com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Small-Font");
	 * javax
	 * .swing.UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel"
	 * ); } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * 
	 * Create and display the form
	 * 
	 * java.awt.EventQueue.invokeLater(new Runnable() {
	 * 
	 * public void run() { new GameFrame(null, null, TABLE).setVisible(true); }
	 * }); }
	 */

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel backgroundLabel;
	private javax.swing.JButton betCheckButton;
	private javax.swing.JLabel currentBetLabel;
	private javax.swing.JMenuItem exitGameMenuItem;
	private javax.swing.JMenu fiveCardStudMenu;
	private javax.swing.JButton foldButton;
	private javax.swing.JScrollPane gameConsoleScrollPane;
	private javax.swing.JTextArea gameConsoleTextArea;
	private javax.swing.JLabel gamePotLabel;
	private javax.swing.JLayeredPane gamePotLayeredPane;
	private javax.swing.JMenuItem gameRulesMenuItem;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JLayeredPane mainLayeredPane;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem openChatMenuItem;
	private javax.swing.JMenuItem openStatisticsMenuItem;
	private javax.swing.JLabel opponent1AvatarLabel;
	private javax.swing.JLabel opponent1CardLabel1;
	private javax.swing.JLabel opponent1CardLabel2;
	private javax.swing.JLabel opponent1CardLabel3;
	private javax.swing.JLabel opponent1CardLabel4;
	private javax.swing.JLabel opponent1CardLabel5;
	private javax.swing.JLabel opponent1ChipsLabel;
	private javax.swing.JLayeredPane opponent1LayeredPane;
	private javax.swing.JLabel opponent1NameLabel;
	private javax.swing.JLabel opponent2AvatarLabel;
	private javax.swing.JLabel opponent2CardLabel1;
	private javax.swing.JLabel opponent2CardLabel2;
	private javax.swing.JLabel opponent2CardLabel3;
	private javax.swing.JLabel opponent2CardLabel4;
	private javax.swing.JLabel opponent2CardLabel5;
	private javax.swing.JLabel opponent2ChipsLabel;
	private javax.swing.JLayeredPane opponent2LayeredPane;
	private javax.swing.JLabel opponent2NameLabel;
	private javax.swing.JLabel opponent3AvatarLabel;
	private javax.swing.JLabel opponent3CardLabel1;
	private javax.swing.JLabel opponent3CardLabel2;
	private javax.swing.JLabel opponent3CardLabel3;
	private javax.swing.JLabel opponent3CardLabel4;
	private javax.swing.JLabel opponent3CardLabel5;
	private javax.swing.JLabel opponent3ChipsLabel;
	private javax.swing.JLayeredPane opponent3LayeredPane;
	private javax.swing.JLabel opponent3NameLabel;
	private javax.swing.JLabel opponent4AvatarLabel;
	private javax.swing.JLabel opponent4CardLabel1;
	private javax.swing.JLabel opponent4CardLabel2;
	private javax.swing.JLabel opponent4CardLabel3;
	private javax.swing.JLabel opponent4CardLabel4;
	private javax.swing.JLabel opponent4CardLabel5;
	private javax.swing.JLabel opponent4ChipsLabel;
	private javax.swing.JLayeredPane opponent4LayeredPane;
	private javax.swing.JLabel opponent4NameLabel;
	private javax.swing.JLabel playerAvatarLabel;
	private javax.swing.JTextField playerBetTextField;
	private javax.swing.JLayeredPane playerBettingLayeredPane;
	private javax.swing.JLabel playerCardLabel1;
	private javax.swing.JLabel playerCardLabel2;
	private javax.swing.JLabel playerCardLabel3;
	private javax.swing.JLabel playerCardLabel4;
	private javax.swing.JLabel playerCardLabel5;
	private javax.swing.JLayeredPane playerCardsLayeredPane;
	private javax.swing.JLabel playerChipsLabel;
	private javax.swing.JLayeredPane playerInfoLayeredPane;
	private javax.swing.JLabel playerNameLabel;
	private javax.swing.JMenuItem quitFiveCardStudMenuItem;
	private javax.swing.JMenu viewMenu;
	// End of variables declaration//GEN-END:variables
}
