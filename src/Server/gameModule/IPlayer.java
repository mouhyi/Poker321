package Server.gameModule;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer  extends Remote {

	/**
	 * Gets this player's stakes
	 * 
	 * @return chips - player's chips
	 */
	public double getChips() throws RemoteException;
	
	public int getUserId() throws RemoteException;
	
	public Hand getHand() throws RemoteException;
	
	public Card getFaceDownCard()throws RemoteException;

	public boolean isTurn() throws RemoteException;
	
	public double getCurBet() throws RemoteException ;
	
	
	
}	
