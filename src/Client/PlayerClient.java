package Client;

import java.rmi.Naming;


import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

import GUI.ServerListener;
import GUI.InitiateGameDisplayThread;
import GUI.UpdateAfterRoundThread;
import GUI.UpdateDuringRoundThread;
import Remote.IPlayerClient;
import Remote.RemoteGame;
import Remote.RemoteUser;

/**
 * Wrapper Class for a player : client of the game service Uses Semaphores to
 * ensure order in threads execution
 * 
 * @author mouhyi
 * 
 */
public class PlayerClient extends UnicastRemoteObject implements IPlayerClient {

	private RemoteGame rmGame;
	private int userId;
	private int gameId;

	public Semaphore semA;
	public Semaphore semB;

	public Semaphore mainSem;

	private ServerListener listener;

	/**
	 * Constructor
	 * 
	 * @param userId
	 * @param rmGame
	 * @param listener
	 * @throws RemoteException
	 * @author mouhyi
	 */
	public PlayerClient(int userId, RemoteGame rmGame, ServerListener listener)
			throws RemoteException {
		this.userId = userId;
		this.rmGame = rmGame;
		this.listener = listener;

		semA = new Semaphore(1, true);
		try {
			semA.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semB = new Semaphore(1, true);
		try {
			semB.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		mainSem = new Semaphore(1);

	}

	/**
	 * Looks up the server object Registers itself with the server
	 * 
	 * @param userId
	 * @param rmGame
	 * @param listener
	 * @return
	 * @throws RemoteException
	 */
	// :
	public static PlayerClient createPlayerProxy(int userId, RemoteGame rmGame,
			ServerListener listener) throws RemoteException {

		PlayerClient playerCl = new PlayerClient(userId, rmGame, listener);

		playerCl.rmGame.registerPlayer((IPlayerClient) playerCl);

		return playerCl;

	}

	/**
	 * Initiate the game display
	 * @throws RemoteException
	 */
	public void InitiateGameDisplay() throws RemoteException {

		(new InitiateGameDisplayThread(listener, this)).start();

	}

	public void updateDuringRound(String msg) throws RemoteException {

		(new UpdateDuringRoundThread(listener, rmGame, userId, msg, this))
				.start();

	}
	
	/**
	 * updates game display after a betting round
	 */
	public void updateAfterRound(String msg) throws RemoteException {

		(new UpdateAfterRoundThread(listener, msg, this)).start();

	}
	
	// for testing
	public synchronized boolean isDone() throws RemoteException {
		return true;
	}
	
	/**
	 * Gets the chat message
	 */
	public void getChatMessage(String from, String message)
			throws RemoteException {
		listener.addChatMessage(from, message);
	}

	// Getters
	public RemoteGame getGameProxy() {
		return rmGame;
	}

	public int getUserId() throws RemoteException {
		return userId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setListener(ServerListener listener) {
		this.listener = listener;
	}
	
	// for testing
	public int foo() throws RemoteException {
		System.out.println("FOOOOOOO");
		return 123;
	}
}
