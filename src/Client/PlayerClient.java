package Client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import GUI.ServerListener;
import Server.gameModule.RemoteGame;
import Server.userModule.RemoteUser;

public class PlayerClient extends UnicastRemoteObject  implements IPlayerClient {

	private RemoteGame rmGame;
	private int userId;
	private int gameId;
	
	private ServerListener listener;

	public PlayerClient(int userId, RemoteGame rmGame, ServerListener listener) throws RemoteException {
		this.userId = userId;
		this.rmGame = rmGame;
		this.listener = listener;
	}

	// Looks up the server object
	// Registers itself
	// Invokes the business methods on the server object:
	public static PlayerClient createPlayerProxy (int userId, RemoteGame rmGame, ServerListener listener)
			throws RemoteException {

		PlayerClient playerCl = new PlayerClient(userId, rmGame, listener);

		playerCl.rmGame.registerPlayer((IPlayerClient)playerCl);
	
		return playerCl;

	}
	
	public void InitiateGameDisplay()  throws RemoteException{
		System.out.println("Call back for game init");
		listener.enterGameFrame();
		//listener.initializeGame();
		//listener.addInGameConsoleMessage("Welcome to the game!");
	}
	
	public void updateDuringRound(String msg)  throws RemoteException{
		listener.updateCurrentBet();
		listener.addInGameConsoleMessage(msg);
	}
	
	public void updateAfterRound(String msg)  throws RemoteException{
		listener.updateAllCards();
		listener.updateBettingSystem();
		listener.addInGameConsoleMessage(msg);
	}
	
	

	// Getters
	public RemoteGame getGameProxy() {
		return rmGame;
	}

	public int getUserId() throws RemoteException{
		return userId;
	}

	public int getGameId() {
		return gameId;
	}
	
	public void setListener(ServerListener listener){
		this.listener = listener;
	}
	
}
