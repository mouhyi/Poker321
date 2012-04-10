package Server.gameModule;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IGameTable extends Remote {
	 
	/** returns the hostId
	 * 
	 * @return
	 * @author Peter
	 */
	public int getHostId();

	/**
	 * Returns the table id
	 * 
	 * @return
	 * @author Peter
	 */
	public int getTableId();

	/**
	 * Returns the list of players in the game
	 * 
	 * @return
	 * @author Peter
	 */
	public ArrayList<IPlayer> getPlayers();

	/**
	 * Returns the ante of the game
	 * 
	 * @return
	 * @author Peter
	 */
	public int getAnte();

	/**
	 * This adds a player to the player list
	 * 
	 * @param playerId
	 * @return
	 * @author Peter, Mouhyi
	 */
	public int addPlayer(int playerId);
	/**
	 * This removes a player from the player list
	 * 
	 * @param player
	 * @author mouhyi
	 */
	public void removePlayer(int userId) ;
	
	/**
	 * Changes the ante parameter of this table
	 * @param newAnte
	 * @return
	 */
	public int changeAnte(int newAnte) ;
	
	/**
	 * This returns the bringIn value
	 * 
	 * @param newBringIn
	 * @return
	 */
	public double getBringIn();
	
	/**
	 * Changes the bringIn parameters of the game
	 * @param newBringIn
	 * @return
	 */
	public double changeBringIn(double newBringIn);
	
	/**
	 * 
	 * @return the current game in the table
	 */
	public RemoteGame getGame();
	
	/**
	 * 
	 * @return The name of this table
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException ;
	
	/**
	 * Initiates a new game
	 */
	public void initiateGame() throws RemoteException ;
	
	/**
	 * Starts a new Game
	 */
	public void startGame()throws RemoteException ;
}
