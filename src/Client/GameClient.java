package Client;

import java.rmi.Naming;

import Remote.GameServer;
import Remote.RemoteUser;

public class GameClient {
	
	private static final int PORT = 10066;
	private static final String HOST_NAME = "localhost";
	
	// stub to the GameServer object
	private GameServer gameServerProxy;
	
	/**
	 * Contructor
	 * @param name
	 */
	public GameClient(){
		try{
			gameServerProxy = (GameServer) Naming.lookup( "rmi://" + HOST_NAME + ":" + Integer.toString(PORT)+"/GameServer" );;
		}catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Getter
	 * @return a stub to the UserServer object
	 */
	public GameServer getUserProxy() {
		return gameServerProxy;
	}
}
