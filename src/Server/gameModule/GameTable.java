package Server.gameModule;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import Remote.IGameTable;
import Remote.IPlayer;
import Remote.UserObject;
import Server.gameModule.*;

/**
 * Wrapper Class for a poker table
 * 
 * @author mouhyi, Peter
 * 
 */
public class GameTable extends UnicastRemoteObject  implements IGameTable {

	private int hostId;
	private int tableId;
	private ArrayList<Player> players;
	private int ante;
	private double bringIn;
	private String name;
	private static int numberOfTables = 0;
	private Game curGame;

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param ante
	 * @param game
	 * @param bringIn
	 * @param players
	 * @author Peter, mouhyi
	 */
	public GameTable(int ante, int hostId, ArrayList<Integer> playersId,
			double bringIn, String suggestedName) throws RemoteException {
		this.hostId = hostId;
		this.ante = ante;
		this.tableId = numberOfTables + 1;
		// create players
		players = new ArrayList<Player>();
		for (Integer id : playersId){
			Player currentPlayer=null;
			try {
				currentPlayer = new Player(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			players.add(currentPlayer);
		}
		this.name = suggestedName;
		this.bringIn = bringIn;
		this.numberOfTables++;
		
		// register table with rmi
	}

	/**
	 * returns the hostId
	 * 
	 * @return
	 * @author Peter
	 */
	public int getHostId() throws RemoteException{
		return this.hostId;
	}

	/**
	 * Returns the table id
	 * 
	 * @return
	 * @author Peter
	 */
	public int getTableId() throws RemoteException{
		return this.tableId;
	}

	/**
	 * Returns the list of players in the game
	 * 
	 * @return
	 * @author Peter
	 */
	public ArrayList<IPlayer> getPlayers() throws RemoteException {
		ArrayList<IPlayer> cpy;
		synchronized(players){
			cpy = new ArrayList<IPlayer>();
			for(Player p: players){
				cpy.add((IPlayer)p);
			}
		}
		return cpy;
	}

	/**
	 * Returns the ante of the game
	 * 
	 * @return
	 * @author Peter
	 */
	public int getAnte() throws RemoteException {
		return this.ante;
	}

	/**
	 * This adds a player to the player list
	 * 
	 * @param playerId
	 * @return
	 * @author Peter, Mouhyi
	 */
	public int addPlayer(int playerId) throws RemoteException {
		Player p = null;
		try {
			p = new Player(playerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (players) {
			if (this.players.size() < 5 && p != null) {
				this.players.add(p);
				return 0;
			}
		}
		return -1;
	}

	/**
	 * This removes a player from the player list
	 * 
	 * @param player
	 * @author mouhyi
	 */
	public void removePlayer(int userId)  throws RemoteException{
		Player player = this.getPlayer(userId);
		synchronized (players) {
			if (player != null) {
				players.remove(player);
			}
		}
	}
	/**
	 * This changes the minimum ante of the game
	 * 
	 * @param newante
	 * @author Peter
	 */
	public int changeAnte(int newAnte)  throws RemoteException{
		this.ante = newAnte;
		return this.ante;
	}


	/**
	 * This returns the bringIn value that the player
	 *
	 * @return
	 * @author Peter
	 */
	public double getBringIn() throws RemoteException {
		return this.bringIn;
	}


	/**
	 * This changes the Bringin value
	 * @param newBringin
	 * @return
	 * @author Peter
	 */
	public double changeBringIn(double newBringIn) throws RemoteException {
		this.bringIn = newBringIn;
		return this.bringIn;
	}
	
	/**
	 * 
	 * @return the current game in the table
	 */
	public Game getGame() throws RemoteException{
		return curGame;
	}

	public void initiateGame() throws RemoteException {
			curGame = new Game(this.ante, this.bringIn, new ArrayList<Player>(), this.tableId);
	}

	public String getName() throws RemoteException {
		return name;
	}

	public void startGame() throws RemoteException {
		try {
			curGame.addPlayers(this.players);
			
			
			
			// new thread here: Solves the bug
			//curGame.play();
			(new GameThread(curGame)).start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets player with userId
	 * 
	 * @param userId
	 * @return
	 * @throws RemoteException
	 */
	public Player getPlayer(int userId) throws RemoteException {
		for (Player p : players) {
			try {
				if (p.getUserId() == userId) {
					return p;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
