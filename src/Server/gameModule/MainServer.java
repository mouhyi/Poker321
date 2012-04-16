package Server.gameModule;

import Server.userModule.UserHost;


/**
 * Starting point of the server
 * @author mouhyi
 *
 */
public class MainServer {
	
	public static void main(String [] args){
		UserHost user = new UserHost();
		RMIServer.registerGameServer();
	}
	
}
