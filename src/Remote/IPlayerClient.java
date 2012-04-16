package Remote;

import java.rmi.Remote;

import java.rmi.RemoteException;

/**
 * Player Client Remote Interface
 * @author mouhyi
 *
 */

public interface IPlayerClient extends Remote {
	
	/**
	 * Initiates Game
	 * @throws RemoteException
	 */
	public void InitiateGameDisplay()  throws RemoteException;
	
	/**
	 * updates game during betting round
	 * @param msg
	 * @throws RemoteException
	 */
	public void updateDuringRound(String msg)  throws RemoteException;
	
	/**
	 * updates game after a betting round
	 * @param msg
	 * @throws RemoteException
	 */
	public void updateAfterRound(String msg)  throws RemoteException;
	
	/**
	 * gets message
	 * @param from: sender
	 * @param message
	 * @throws RemoteException
	 */
	public void getChatMessage(String from, String message)throws RemoteException;
	
	/**
	 * getter for userId
	 * @throws RemoteException
	 */
	public int getUserId() throws RemoteException;
	
	/**
	 * checks if a user is done
	 * @return
	 * @throws RemoteException
	 */
	public boolean isDone() throws RemoteException;
	
	// testing
	public int foo() throws RemoteException;

}
