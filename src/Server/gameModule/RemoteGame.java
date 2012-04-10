package Server.gameModule;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Client.IPlayerClient;

/**
 * This interface defines all of the remote features offered by the Game server
 * 
 * @author mouhyi
 * 
 */
public interface RemoteGame extends Remote {

	/**
	 * Runs a five card stud Game
	 */
	public void play() throws RemoteException;

	/**
	 * deletes a player from this game. Should be called when a player folds out
	 * 
	 * @param userId
	 *            - id of player to be removed
	 */
	public void removePlayer(int userId) throws RemoteException;

	/**
	 * Registers rmi Player observer
	 * 
	 * @param p
	 */
	public void registerPlayer(IPlayerClient p) throws RemoteException;

	/**
	 * Updates the game state with a player calling the current bet
	 * 
	 * @param userId
	 *            - userId of the player making the bet
	 * @return 0 if player has sufficient chips, -1 otherwise, -2 if it's not
	 *         player's turn
	 */
	public int call(int userId) throws RemoteException;

	/**
	 * Updates the game state whith a player raising the current bet
	 * 
	 * @param userId
	 *            - userId of the player making the bet player
	 * @param bet
	 *            - value of the raise
	 * @return 0 on success, -1 on failure, -2 if it's not player's turn
	 */
	public int raise(int userId, double bet) throws RemoteException;

	/**
	 * Must be called when a player goes all in
	 * 
	 * @param userId
	 *            - player all in
	 */
	// TODO create All-in Button
	public int allIn(int userId) throws RemoteException;

	// getters

	public int getId() throws RemoteException;

	public ArrayList<IPlayer> getPlayers() throws RemoteException;

	public int getRound() throws RemoteException;

	public double getPot() throws RemoteException;

	public double getCurBet() throws RemoteException;

	public double getAnte() throws RemoteException;

	public double getBringIn() throws RemoteException;

	public int getCurPlayerId() throws RemoteException;
}
