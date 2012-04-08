package Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Server.gameModule.RemoteGame;
import Server.userModule.RemoteUser;

public class PlayerClient extends UnicastRemoteObject  implements PlayerClientRemote {

	private static final int PORT = 10002;
	private static final String HOST_NAME = "localhost";
	private static final long serialVersionUID = 1L;

	private RemoteGame rmGame;
	private int userId;
	private int gameId;

	protected PlayerClient(int userId, int gameId) throws RemoteException {
		this.userId = userId;
		this.gameId = gameId;
	}

	// Looks up the server object
	// Registers itself
	// Invokes the business methods on the server object:
	public static PlayerClient createPlayerProxy(int userId, int gameId)
			throws RemoteException {

		PlayerClient playerCl = new PlayerClient(userId, gameId);

		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		try {
			playerCl.rmGame = (RemoteGame) Naming.lookup("rmi://" + HOST_NAME
					+ ":" + Integer.toString(PORT) + "/Game" + gameId);
			playerCl.rmGame.registerPlayer(playerCl);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return playerCl;
		}
	}

	// Getters
	public RemoteGame getRmGame() {
		return rmGame;
	}

	public int getUserId() {
		return userId;
	}

	public int getGameId() {
		return gameId;
	}
}
