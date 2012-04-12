package GUI;

import Client.PlayerClient;

public class InitiateGameDisplayThread extends Thread {
	
	ServerListener sl;
	PlayerClient playerCl;
	
	public InitiateGameDisplayThread(ServerListener sl, PlayerClient playerCl){
		this.sl = sl;
		this.playerCl = playerCl;
	}
	
	public void run(){
		
		/*try {
			playerCl.semA.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//synchronized(playerCl)
		System.out.println("INIT ENTER FM     ****************");
		sl.enterGameFrame();
		
		System.out.println( " INIT GAME     *****************");
		sl.initializeGame();
		
		System.out.println( " INIT RELEASE SEM     *****************");
		
		
		playerCl.semA.release();
		
		//playerCl.mainSem.release();
	}

}
