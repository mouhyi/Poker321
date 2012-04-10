package Server.gameModule;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	
	private static final int PORT = 10066;
	private static final String HOST_NAME = "localhost";
	
	public static void registerGameServer() {
		try {
			
			GameServer gs =new GameServerImpl();
			
			// Reference to registry service by creating registry service
			LocateRegistry.createRegistry(PORT);
			// Register server object to registry with unique name
			String urlString = "//" + HOST_NAME + ":" + Integer.toString(PORT)
					+ "/" + "GameServer";
			Naming.rebind(urlString, gs);
			
			
		} catch (java.rmi.UnknownHostException uhe) {
			System.out.println("The host computer name you have specified, "
					+ HOST_NAME + " does not match your real computer name.");
			
		} catch (RemoteException re) {
			System.out.println("Error starting service"+re);
		} catch (MalformedURLException mURLe) {
			System.out.println("Internal error" + mURLe);
		}
		
	}
	
	
	public static void main(String [] args){
		registerGameServer();
	}
}
