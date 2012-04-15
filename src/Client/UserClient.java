package Client;

import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import GUI.ServerListener;
import Server.userModule.RemoteUser;
import java.util.concurrent.*;


/**
 * Get reference to server’s registry 
 * Locate server object from server’s registry service
 * @author mouhyi
 *
 */
public class UserClient extends UnicastRemoteObject implements IUserClient  {
	
	private static final int PORT = 10012;
	private static final String HOST_NAME  = "localhost"; //"localhost"
	
	
	// stub to the UserServer object
	private RemoteUser userProxy;
	private int userId;
	private ServerListener sl;
	
	/**
	 * Contructor
	 * @param name
	 */
	public UserClient(ServerListener sl) throws RemoteException{
		this.sl = sl;
		try{
			userProxy = (RemoteUser) Naming.lookup( "rmi://" + HOST_NAME + ":" + Integer.toString(PORT)+"/UserServer" );
			//userProxy.registerUser(this);
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
	
	public void registerWithServer(int userId){
		
		this.userId = userId;
		
		try {
			userProxy.registerUser(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getuserId() throws RemoteException{
		return this.userId;
	}
	
	/**
	 * display notification message
	 * @param msg
	 * @throws RemoteException
	 */
	@Override
	public void showNotificationMessage(String msg) throws RemoteException{
		(new InviteThread(this.sl, msg, this)).start();	
	}
}
