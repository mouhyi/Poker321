package GUI;

import java.rmi.RemoteException;

import Server.gameModule.RemoteGame;

public class UpdateAfterRoundThread extends Thread {

	private ServerListener listener;
	private String msg;
	
	public UpdateAfterRoundThread(ServerListener sl, String msg){
		this.listener = sl;
		this.msg = msg;
	}
	
	public void run(){
		listener.updateAllCards();
	 	System.out.println("updated cards ");
		listener.updateBettingSystem();
		System.out.println("updated bets");
		listener.addInGameConsoleMessage(msg);
		System.out.println("updated message");
		
	}
}
