package Client;

import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;

import Server.userModule.RemoteUser;
import java.util.concurrent.*;


/**
 * Get reference to server’s registry 
 * Locate server object from server’s registry service
 * @author mouhyi
 *
 */
public class UserClient {
	// stub to the UserServer object
	private RemoteUser userProxy;
	
	/**
	 * Contructor
	 * @param name
	 */
	public UserClient(){
		try{
			Registry registry = LocateRegistry.getRegistry();
			userProxy = (RemoteUser) registry.lookup("UserServer");
		}catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Getter
	 * @return a stub to the UserServer object
	 */
	public RemoteUser getUserProxy() {
		return userProxy;
	}
}
