package Server.gameModule;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICard extends Remote {
	public Rank getRank() throws RemoteException;
	public Suit getSuit() throws RemoteException;
}
