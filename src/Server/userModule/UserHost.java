package Server.userModule;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Remote.RemoteUser;

/**
 * This class is a server process to host the Remote RMI User services.
 * 
 * @author mouhyi
 * 
 */
public class UserHost {

	private static final int PORT = 10012;
	private static final String HOST_NAME = "132.206.42.61";

	/**
	 * This is constructor where the Remote 'User' Object is exported to the RMI
	 * Registry It must be called on the server before the client initiates an
	 * RMI connection on a User object
	 * 
	 * @author mouhyi
	 */
	public UserHost() {
		try {
			// Create server object
			RemoteUser user = new UserImpl();
			// Reference to registry service by creating registry service
			LocateRegistry.createRegistry(PORT);
			// Register server object to registry with unique name
			String urlString = "//" + HOST_NAME + ":" + Integer.toString(PORT)
					+ "/" + "UserServer";
			Naming.rebind(urlString, user);;

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
