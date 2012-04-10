package Server.gameModule;

import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class represents the ongoing games
 * keep tracks of live tables
 * 
 * @author mouhyi
 *
 */
public class GameLobby {
	private ArrayList<GameTable> pokerTables;
	
	public ArrayList<GameTable> getTables(){
		return pokerTables;
	}
	
	public GameTable getTableByName(String name){
		for (GameTable t: pokerTables){
			try {
				if(t.getName().equals(name)){
					return t;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
