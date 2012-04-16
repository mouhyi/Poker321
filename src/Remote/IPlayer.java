package Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Player Remote interface
 * @author mouhyi
 *
 */
public interface IPlayer  extends Remote {

	/**
	 * Gets this player's stakes
	 * 
	 * @return chips - player's chips
	 */
	public double getChips() throws RemoteException;
	
	/**
	 * Get userID
	 * @return
	 * @throws RemoteException
	 */
	public int getUserId() throws RemoteException;
	
	/**
	 * get player's hand
	 * @return
	 * @throws RemoteException
	 */
	public IHand getHand() throws RemoteException;
	
	/**
	 * get player's facedown card
	 * @return
	 * @throws RemoteException
	 */
	public ICard getFaceDownCard()throws RemoteException;

	/**
	 * Checks whether it's a user's turn
	 * @return
	 * @throws RemoteException
	 */
	public boolean isTurn() throws RemoteException;
	
	/**
	 * get player's current bet
	 * @return
	 * @throws RemoteException
	 */
	public double getCurBet() throws RemoteException ;
	
}	
