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
import Server.gameModule.RemoteGame;
import Server.userModule.RemoteUser;

public class PlayerClient extends UnicastRemoteObject  implements IPlayerClient {

	private RemoteGame rmGame;
	private int userId;
	private int gameId;
	
	public Semaphore semA;
	public Semaphore semB;
	
	public Semaphore mainSem;
	
	private ServerListener listener;

	public PlayerClient(int userId, RemoteGame rmGame, ServerListener listener) throws RemoteException {
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
		
		/*System.out.println("Call back for game init");
		
		
		System.out.println("TESTSTGAME");
		listener.enterGameFrame();
		
		System.out.println( " INIIIII  TESTSTGAME");
		//listener.initializeGame();
		semA.release();*/
		  
		
		// FINAL: Has to be in a new Thread
		(new InitiateGameDisplayThread(listener, this)).start();
		
		// wont work, create another blck method to call fromserver
		
		//listener.addInGameConsoleMessage("Welcome to the game!");
		
		//semA.release();
	}
	
	public void updateDuringRound(String msg)  throws RemoteException{
		
		/*System.out.println( " Pcl.update********* START ***********");
		try {
			semA.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//semA.release();	// remove
		
		System.out.println( " Pcl.update*********** END *********");*/
		
		
		(new UpdateDuringRoundThread(listener, rmGame, userId, msg, this)).start();

		
		/*listener.updateCurrentBet();
		listener.updatePot();
		listener.addInGameConsoleMessage(msg);
		try {
			if(rmGame.getPlayer(userId).isTurn())
				listener.notifyPlayerTurn();
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
		
		//semB.release();
	}
	
	public void updateAfterRound(String msg)  throws RemoteException{
		
		/*try {
			semB.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Now in playerclient, update after round*********");*/
		
		(new UpdateAfterRoundThread(listener, msg, this)).start();
		
		
		/*listener.updateAllCards();
	 	System.out.println("updated cards ");
		listener.updateBettingSystem();
		System.out.println("updated bets");
		listener.addInGameConsoleMessage(msg);
		System.out.println("updated message");*/
		
	}
	
	
	public synchronized boolean isDone() throws RemoteException{
		/*try {
			mainSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		mainSem.release();*/
		return true;
	}
	
	
	public void getChatMessage(String from, String message)throws RemoteException{
		listener.addChatMessage(from, message);
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
	
	public int foo() throws RemoteException{
		System.out.println("FOOOOOOO"); 
		return 123;
	}
}
