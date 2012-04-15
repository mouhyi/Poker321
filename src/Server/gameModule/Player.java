package Server.gameModule;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import Server.userModule.UserImpl;
import Server.userModule.UserObject;

/**
 * Wrapper class for a poker player
 * @author mouhyi
 *
 */
public class Player extends UnicastRemoteObject implements Comparable<Player>, IPlayer{

	private static final long serialVersionUID = 1L;
	
	private int userId;
	private Hand hand;
	private Card faceDownCard;
	// player's stake 
	private double chips;
	private double curBet;
	private boolean turn;
	private int seat;
	
	private boolean done;
	
	/**
	 * Constructor
	 * @param id
	 * @throws RemoteException
	 * @throws SQLExceptionPlayerClient
	 */
	public Player(int id) throws RemoteException, SQLException{
		userId = id;
		chips = new UserImpl().getUserObject(id).getChips();
		hand = new Hand();
		turn = false;
		seat =-1;
		done = true;
		
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
		}else{
			hand.add(card);
		}	
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
	public double getChips() throws RemoteException{
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
	 * Should be called when a player leaves the table (not at the end of the game)
	 * 
	 * @return 0 on success, -1 on failure
	 * @throws SQLException 
	 * @throws RemoteException
	 * @author mouhyi
	 * 
	 */
	public int updateChips() throws RemoteException, SQLException{
		UserObject user =(new UserImpl()).getUserObject(userId);
		user.setChips( chips);
		return (new UserImpl()).editProfile(user) ;
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
	public int getUserId() throws RemoteException{
		return userId;
	}

	public Hand getHand() throws RemoteException{
		return hand;
	}

	public Card getFaceDownCard() throws RemoteException{
		return faceDownCard;
	}

	public boolean isTurn() throws RemoteException{
		return turn;
	}

	public int getSeat() throws RemoteException{
		return seat;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public double getCurBet() throws RemoteException {
		return curBet;
	}

	
}
