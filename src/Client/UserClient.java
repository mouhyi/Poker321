package Client;

import java.rmi.Naming;
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
	
	private static final int PORT = 10002;
	private static final String HOST_NAME  = "localhost";
	
	
	// stub to the UserServer object
	private RemoteUser userProxy;
	
	/**
	 * Contructor
	 * @param name
	 */
	public UserClient(){
		try{
			userProxy = (RemoteUser) Naming.lookup( "rmi://" + HOST_NAME + ":" + Integer.toString(PORT)+"/UserServer" );;
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
