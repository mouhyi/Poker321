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
public class Player {

	private UserObject user;
	private Hand hand;
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
	public void addCard(Card card, boolean down){
		if(down) {
			card.setDown();
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
	
	
}
