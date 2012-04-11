package GUI;

import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This class should only be called by the server to update fields with the GUI.
 * @author mattvertescher
 */
public class ServerListener {
    
    private MainMenuFrame mainMenuFrame; 
    private GameFrame gameFrame;
    private ChatFrame chatFrame; 
    
    /**
     * Constructor that assigns frame variables. 
     */
    public ServerListener() {
        mainMenuFrame = null;
        gameFrame = null;
        chatFrame = null;
    }
    
    /**
     * Setter for mainMenuFrame.
     * @param mmf 
     */
    public void setMainMenuFrame(MainMenuFrame mmf) {
        mainMenuFrame = mmf;
    }
    
    /**
     * Setter for gameFrame.
     * @param gf 
     */
    public void setGameFrame(GameFrame gf) {
        gameFrame = gf;
    }
    
    /**
     * Setter for chatFrame. 
     * @param cf 
     */
    public void setChatFrame(ChatFrame cf) {
        chatFrame = cf;
    }
    
    /**
     * Sets main menu reference to null.
     */
    public void releaseMainMenuFrame() {
        mainMenuFrame = null;
    }
    
    /**
     * Sets game frame reference to null.
     */
    public void releaseGameFrame() {
        gameFrame = null;
    }
    
    /**
     * Sets chat frame reference to null.
     */
    public void releaseChatFrame() {
        chatFrame = null;
    }
    
    
    
    /*
     * Main menu push notifications.
     */
    
    /**
     * Displays a pop-up message to the main menu
     * @param message
     * @return completed 
     */
    public boolean sendNotificationMessage(String message) {
        if (mainMenuFrame != null) 
            return mainMenuFrame.displayPopUp(message);
        return false; 
    }
    
    /**
     * Adds a new message to the main menu. 
     * @param message
     * @return completed 
     */
    public boolean addGameLobbyConsoleMessage(String message) {
        if (mainMenuFrame != null) 
            return mainMenuFrame.addGameLobbyConsoleMessage(message);
        return false;
    }
    
    /**
     * Tells the main menu to enter into the game frame.
     * @return 
     */
    public boolean enterGameFrame() {
    	System.out.println("Sl: enter game frame");
        if (mainMenuFrame != null) {
        	System.out.println("Sl: mmf not null");
            return mainMenuFrame.enterGameFrame();                      
        }
            
        return false;
    }
    
    
    /*
     * Game push notifications.
     */
    
    
    /**
     * Method called by server to initialize all game fields.
     * @return 
     */
    public boolean initializeGame() {
    	System.out.println("Sl: init game");
        if (gameFrame != null) 
            return gameFrame.resetGame();   
        return false;
    }
    
    /**
     * Tells the game frame to update all player cards.
     * @param username
     * @return 
     */
    public boolean updateAllCards() {
        if (gameFrame != null) 
            return gameFrame.updateAllCards();
        return false;
    }
    
    /**
     * Tells the game frame to update a players hand.
     * @param username
     * @return 
     */
    public boolean updatePlayerHand(String username) {
        if (gameFrame != null) 
            return gameFrame.updateCardsForUser(username);
        return false;
    }
    
    /**
     * Updates the game betting system that includes the pot, current bet, and 
     * the chips for each user.
     * @return completed 
     */
    public boolean updateBettingSystem() {
        if (gameFrame != null) 
            return gameFrame.updateBettingSystem();
        return false;
    }
    
    /**
     * Update pot for game frame.
     * @return completed
     */
    public boolean updatePot() {
        if (gameFrame != null) 
            return gameFrame.updatePot();
        return false;
    }
    
    /**
     * Update current bet for game frame.
     * @return 
     */
    public boolean updateCurrentBet() {
         if (gameFrame != null) 
            return gameFrame.updateCurrentBet();
        return false; 
    }
    
    /**
     * Update a user's chips label in game frame.
     * @param username
     * @return 
     */
    public boolean updateUserChips(String username) {
         if (gameFrame != null) 
            return gameFrame.updateChipsForUser(username);
        return false; 
    } 
    
    /**
     * Notifies the player that it is their turn.
     * @return 
     */
    public boolean notifyPlayerTurn() {
        if (gameFrame != null) {
            gameFrame.addMessageToInGameConsole("It is your turn.");
            return gameFrame.startTurn();
        }    
        return false;
    }
    
    /**
     * Tells gameFrame to remove a user from the game
     * @param username
     * @return 
     */
    public boolean removeUserFromGame(String username) {
        if (gameFrame != null)             
            return gameFrame.removeUserFromGame(username);
        return false;
    }

    /**
     * Tell the game frame to close and open the main menu frame.
     * @return completed
     * @throws RemoteException
     * @throws SQLException 
     */
    public boolean exitGame() throws RemoteException, SQLException {
        if (gameFrame != null) 
            return gameFrame.exitGame();       
        return false;
    }
   
    /**
     * Adds a new message to the in game console.
     * @param message
     * @return completed
     */
    public boolean addInGameConsoleMessage(String message) {
        if (gameFrame != null) 
            return gameFrame.addMessageToInGameConsole(message);
        return false;
    }
   
    
    
    /*
     * Chat push notifications. 
     */
    
    /**
     * Tells the chat frame to add a new message from a user to its text field.
     * @param from
     * @param message
     * @return completed
     */
    public boolean addChatMessage(String from, String message) {
        if (chatFrame != null) 
            return chatFrame.addMessageToChat(from, message);
        return false;
    }
    
    
}
