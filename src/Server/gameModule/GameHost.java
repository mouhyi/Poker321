package Server.gameModule;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Remote.RemoteGame;
import Remote.RemoteUser;
import Server.userModule.UserImpl;

/**
 * This class is a server process to host the Remote RMI Game services.
 * 
 * @author mouhyi
 * 
 */

public class GameHost {
	
	private static final int PORT = 10004;
	private static final String HOST_NAME = "localhost";

	/**
	 * This is constructor where the Remote 'Game' Object is exported to the RMI
	 * Registry It must be called on the server before the client initiates an
	 * RMI connection on a Game object
	 * 
	 * @author mouhyi
	 */
	public static void RegisterGame(RemoteGame game) {
		try {
			
			// Reference to registry service by creating registry service
			LocateRegistry.createRegistry(PORT);
			// Register server object to registry with unique name
			String urlString = "//" + HOST_NAME + ":" + Integer.toString(PORT)
					+ "/" + "Game"+game.getId();
			Naming.rebind(urlString, game);

		} catch (java.rmi.UnknownHostException uhe) {
			System.out.println("The host computer name you have specified, "
					+ HOST_NAME + " does not match your real computer name.");
		} catch (RemoteException re) {
			System.out.println("Error starting service"+re);
		} catch (MalformedURLException mURLe) {
			System.out.println("Internal error" + mURLe);
		}
		
	}
}
