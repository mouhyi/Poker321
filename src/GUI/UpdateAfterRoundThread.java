package GUI;

import java.rmi.RemoteException;

import Client.PlayerClient;
import Server.gameModule.RemoteGame;

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
			playerCl.semB.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Now in playerclient, update after round*********");
		
		listener.updateAllCards();
	 	System.out.println("updated cards ");
		listener.updateBettingSystem();
		System.out.println("updated bets");
		listener.addInGameConsoleMessage(msg);
		System.out.println("updated message");
		
	}
}
