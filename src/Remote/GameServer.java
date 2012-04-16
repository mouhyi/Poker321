package Remote;

import java.rmi.*;
import java.util.ArrayList;

/**
 *  Main RMI Server
 *  
 * @author mouhyi
 *
 */
public interface GameServer extends Remote {
	
	/**
	 * Creates a new GameTable on the server and returns a reference to it
	 * 
	 * @param ante
	 * @param hostId
	 * @param playersId
	 * @param bringIn
	 * @param suggestedName
	 * @return IGameTable- the new GameTable created
	 * @throws RemoteException
	 * @author mouhyi
	 */
	public IGameTable createTable(int ante, int hostId, ArrayList<Integer> playersId,
			double bringIn, String suggestedName) throws RemoteException;
	
	public ArrayList<IGameTable> getAllTables() throws RemoteException;
	
	public IGameTable getTable(String name) throws RemoteException;
	
	public boolean tableWithName(String name) throws RemoteException;
}
