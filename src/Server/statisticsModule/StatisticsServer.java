package Server.statisticsModule;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import Server.gameModule.GameServer;
import Server.gameModule.GameServerImpl;

public class StatisticsServer {
	private static final int PORT = 10077;
	private static final String HOST_NAME = "142.157.146.89";
	
	public static void registerStatsServer() {
		try {
			
			IStatistics gs =new LeaderBoard();
			
			// Reference to registry service by creating registry service
			LocateRegistry.createRegistry(PORT);
			// Register server object to registry with unique name
			String urlString = "//" + HOST_NAME + ":" + Integer.toString(PORT)
					+ "/" + "StatsServer";
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
		registerStatsServer();
	}
}
