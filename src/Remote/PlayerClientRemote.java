package Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerClientRemote extends Remote {
	
	public void updateAfterRound(String msg)  throws RemoteException;
	public void updateDuringRound(String msg)  throws RemoteException;
	public void InitiateGameDisplay()  throws RemoteException;
	
	public boolean isDone() throws RemoteException;
	
}
