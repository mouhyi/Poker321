package GUI;

import java.rmi.RemoteException;


import Client.PlayerClient;
import Remote.RemoteGame;

/**
 * Thread to update a game state during betting round
 * 
 * @author mouhyi
 * 
 */
public class UpdateDuringRoundThread extends Thread {

	private ServerListener listener;
	private RemoteGame rmGame;
	private int userId;
	private String msg;
	private PlayerClient playerCl;

	public UpdateDuringRoundThread(ServerListener sl, RemoteGame rmGame,
			int userId, String msg, PlayerClient playerCl) {
		this.listener = sl;
		this.rmGame = rmGame;
		this.userId = userId;
		this.msg = msg;
		this.playerCl = playerCl;
	}
	
	/**
	 * Start thread
	 */
	public void run() {

		try {
			playerCl.mainSem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			playerCl.semA.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		playerCl.semA.release(); // remove

		listener.updateCurrentBet();
		listener.updatePot();
		listener.addInGameConsoleMessage(msg);

		try {
			if (rmGame.isPlayerIn(userId)) {

				if (rmGame.getPlayer(userId).isTurn())
					listener.notifyPlayerTurn();
			} else
				listener.removeUserFromGame(userId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		playerCl.semB.release();

		playerCl.mainSem.release();

	}
}
