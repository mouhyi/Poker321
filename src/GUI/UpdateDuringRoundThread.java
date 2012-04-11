package GUI;

import java.rmi.RemoteException;

import Server.gameModule.RemoteGame;

public class UpdateDuringRoundThread extends Thread{
	
	private ServerListener listener;
	private RemoteGame rmGame;
	private int userId; 
	private String msg;
	
	public UpdateDuringRoundThread(ServerListener sl, RemoteGame rmGame, int userId, String msg){
		this.listener = sl;
		this.rmGame= rmGame;
		this.userId = userId;
		this.msg = msg;
	}
	
	public void run(){
		listener.updateCurrentBet();
		listener.updatePot();
		listener.addInGameConsoleMessage(msg);
		try {
			if(rmGame.getPlayer(userId).isTurn())
				listener.notifyPlayerTurn();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
