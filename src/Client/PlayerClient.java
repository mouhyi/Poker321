package Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Server.gameModule.RemoteGame;
import Server.userModule.RemoteUser;

public class PlayerClient extends UnicastRemoteObject  implements PlayerClientRemote {

	private RemoteGame rmGame;
	private int userId;
	private int gameId;

	public PlayerClient(int userId, int gameId) throws RemoteException {
		this.userId = userId;
		this.gameId = gameId;
	}

	// Looks up the server object
	// Registers itself
	// Invokes the business methods on the server object:
	public static PlayerClient createPlayerProxy (int userId, int gameId)
			throws RemoteException {

		PlayerClient playerCl = new PlayerClient(userId, gameId);

		playerCl.rmGame.registerPlayer((IPlayerClient)playerCl);
	
		return playerCl;

	}

	// Getters
	public RemoteGame getGameProxy() {
		return rmGame;
	}

	public int getUserId() {
		return userId;
	}

	public int getGameId() {
		return gameId;
	}
	
}
