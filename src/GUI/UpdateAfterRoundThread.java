package GUI;

import java.rmi.RemoteException;


import Client.PlayerClient;
import Remote.RemoteGame;


/**
 * Thread to update a game state after a betting round
 * 
 * @author mouhyi
 * 
 */
public class UpdateAfterRoundThread extends Thread {

	private ServerListener listener;
	private String msg;
	private PlayerClient playerCl;
	
	public UpdateAfterRoundThread(ServerListener sl, String msg,  PlayerClient playerCl){
		this.listener = sl;
		this.msg = msg;
		this.playerCl = playerCl;
	}
	
	public void run(){
			
		try {
			playerCl.mainSem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		listener.updateAllCards();
	 	System.out.println("updated cards ");
		listener.updateBettingSystem();
		System.out.println("updated bets");
		listener.addInGameConsoleMessage(msg);
		System.out.println("updated message");
		
		playerCl.mainSem.release();
		
		
	}
}
