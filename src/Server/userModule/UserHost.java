package Server.userModule;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is a server process to host the Remote RMI User services.
 * 
 * @author mouhyi
 * 
 */
public class UserHost {

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
			Registry registry = LocateRegistry.getRegistry();
			// Register server object to registry with unique name
			registry.rebind("UserServer", user);

		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
}
