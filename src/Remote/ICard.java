package Remote;

import java.rmi.Remote;

import java.rmi.RemoteException;

import Remote.Rank;
import Remote.Suit;

public interface ICard extends Remote {
	public Rank getRank() throws RemoteException;
	public Suit getSuit() throws RemoteException;
}
