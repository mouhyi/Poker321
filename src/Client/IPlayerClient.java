package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayerClient extends Remote {
	
	public void InitiateGameDisplay()  throws RemoteException;
	public void updateDuringRound(String msg)  throws RemoteException;
	public void updateAfterRound(String msg)  throws RemoteException;
	public void getChatMessage(String from, String message)throws RemoteException;
	public int getUserId() throws RemoteException;
	
	
	public int foo() throws RemoteException;
}
