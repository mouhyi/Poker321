package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserClient extends Remote{
	public int getuserId() throws RemoteException;
	
	/**
	 * display notification message
	 * @param msg
	 * @throws RemoteException
	 */
	public void showNotificationMessage(String msg) throws RemoteException;
}
