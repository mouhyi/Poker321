package Server.gameModule;

import java.rmi.RemoteException;

/**
 * Thread for a single game
 * @author mouhyi
 *
 */
public class GameThread extends Thread {
	
	Game gm;
	
	/**
	 * Constructor
	 * @param gm
	 */
	public GameThread(Game gm){
		this.gm = gm;
	}
	
	/**
	 * Starts the thread
	 */
	public void run(){
		try {
			gm.play();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
