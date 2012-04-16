package GUI;

import Client.PlayerClient;

/**
 * Thread to initialize a game gui
 * 
 * @author mouhyi
 * 
 */
public class InitiateGameDisplayThread extends Thread {
	
	ServerListener sl;
	PlayerClient playerCl;
	
	public InitiateGameDisplayThread(ServerListener sl, PlayerClient playerCl){
		this.sl = sl;
		this.playerCl = playerCl;
	}
	
	public void run(){
		
		sl.enterGameFrame();
		
		sl.initializeGame();

		playerCl.semA.release();
		
		//playerCl.mainSem.release();
	}

}
