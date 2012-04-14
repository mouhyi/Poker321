package Server.gameModule;

import java.rmi.RemoteException;

public class GameThread extends Thread {
	
	Game gm;
	
	public GameThread(Game gm){
		this.gm = gm;
	}
	
	public void run(){
		try {
			gm.play();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
