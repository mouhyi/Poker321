package Server.gameModule;

import java.rmi.RemoteException;
import java.sql.SQLException;

import Server.userModule.UserImpl;
import Server.userModule.UserObject;

/**
 * Wrapper class for a poker player
 * @author mouhyi
 *
 */
public class Player implements Comparable<Player> {

	private UserObject user;
	private Hand hand;
	private Card faceDownCard;
	private double chips;
	private double curBet;
	private double prevBet;
	private boolean myTurn;
	private int seat;
	
	/**
	 * Constructor
	 * @param id
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public Player(int id) throws RemoteException, SQLException{
		user = (new UserImpl()).getUserObject(id);
		chips = user.getChips();
		hand = new Hand();
		myTurn = false;
		seat =-1;
		
	}
	
	/**
	 * Adds a card to this player's hand
	 *  
	 * @param card: card to be added
	 * @param down: wether the card should be up or down
	 * @author mouhyi
	 */
	public void getCard(Card card, boolean down){
		if(down) {
			faceDownCard = card;
		}
		hand.add(card);
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
		user.setChips(chips);
		return (new UserImpl()).editProfile(user) ;
	}
	
	/**
	 * this Player
	 * @param ammount
	 * @return
	 */
	public int bet(double ammount){
		if(ammount > this.chips){
			return -1;
		}
		chips -= ammount;
		return 0;
	}
	
	/**
	 * Retrieves the userId of this player
	 * 
	 * @return userId
	 * @author mouhyi
	 */
	public int getId(){
		return user.getId();
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
	
	public void mergeHand(){
		hand.add(faceDownCard);
	}
	
	
	
	
}
