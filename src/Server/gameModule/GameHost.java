package Server.gameModule;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Server.userModule.RemoteUser;
import Server.userModule.UserImpl;

/**
 * This class is a server process to host the Remote RMI Game services.
 * 
 * @author mouhyi
 * 
 */

public class GameHost {

	/**
	 * This is constructor where the Remote 'Game' Object is exported to the RMI
	 * Registry It must be called on the server before the client initiates an
	 * RMI connection on a Game object
	 * 
	 * @author mouhyi
	 */
	public GameHost() {
		try {
			// Create server object
			RemoteGame game = new GameImpl();
			// Reference to registry service by creating registry service
			Registry registry = LocateRegistry.getRegistry();
			// Register server object to registry with unique name
			registry.rebind("GameServer", game);

		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
}
