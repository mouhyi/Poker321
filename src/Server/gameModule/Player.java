package Server.gameModule;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;

import Server.userModule.UserImpl;
import Server.userModule.UserObject;

/**
 * Wrapper class for a poker player
 * @author mouhyi
 *
 */
public class Player implements Comparable<Player>, Serializable  {

	private static final long serialVersionUID = 1L;
	
	private int userId;
	private Hand hand;
	private Card faceDownCard;
	// player's stake 
	private double chips;
	private double curBet;
	private boolean myTurn;
	private int seat;
	
	/**
	 * Constructor
	 * @param id
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public Player(int id, double stake) throws RemoteException, SQLException{
		userId = id;
		chips = stake;
		hand = new Hand();
		myTurn = false;
		seat =-1;
		
	}
	
	/**
	 * Adds a card to this player's hand
	 *  
	 * @param card: card to be added
	 * @param down: wether the card should be up or down
	 */
	public void getCard(Card card, boolean down){
		if(down) {
			faceDownCard = card;
		}
		hand.add(card);
	}
	

	/**
	 * Bet an ammount of chips
	 * @param ammount
	 * @return
	 */
	// TODO change into isValidBet()
	public int bet(double amount){
		if(amount > this.chips){
			return -1;
		}
		curBet = amount;
		return 0;
	}
	
	/**
	 * Gets this player's stakes
	 * 
	 * @return chips - player's chips
	 */
	public double getChips() {
		return chips;
	}

	/**
	 * Should be called at the end of a betting round
	 * @return
	 */
	public void confirmBet(){
		chips -= curBet;
		curBet =0;
	}
	
	/**
	 * Add chips to the player's stakes
	 * @param ammount
	 */
	public void addChips(double amount){
		chips += amount;
	}
	
	/**
	 * Updates this user's chips column in the DB
	 * Should be called at the end of the game
	 * 
	 * @return 0 on success, -1 on failure
	 * @throws SQLException 
	 * @throws RemoteException
	 * @author mouhyi
	 * 
	 */
	public int updateChips() throws RemoteException, SQLException{
		UserObject user =(new UserImpl()).getUserObject(userId);
		user.setChips(user.getChips() + chips);
		return (new UserImpl()).editProfile(user) ;
	}
	
	/**
	 * Retrieves the userId of this player
	 * 
	 * @return userId
	 * @author mouhyi
	 */
	public int getId(){
		return this.userId;
	}
	
	/**
	 * Implement the comparable interface. IT defines a natural ordering of
	 * players according to their hand values.
	 * 
	 * @param Player:p
	 * @return A negative integer if this player's hand is less than the player's argument hand;
	 *         zero if they are equal;
	 *         a positive number otherwise.
	 * @author mouhyi
	 */
	public int compareTo(Player p) {
		return this.hand.compareTo(p.hand);
	}
	
	/**
	 * Merges the face down card into the hand
	 */
	public void mergeHand(){
		hand.add(faceDownCard);
	}
	
	// Getters
	public int getUserId() {
		return userId;
	}

	public Hand getHand() {
		return hand;
	}

	public Card getFaceDownCard() {
		return faceDownCard;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public int getSeat() {
		return seat;
	}

	
}
