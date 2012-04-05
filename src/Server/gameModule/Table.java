package Server.gameModule;

import java.util.ArrayList;
import java.util.List;

import Server.userModule.UserObject;

/**
 * The table hosting games. 

 * @author mouhyi
 *
 */
public class Table {
	private Game game;
	private UserObject owner;
	ArrayList<Player> players;
	double ante;
	double bringIn;
	
	/**
	 * Constructor
	 * 
	 * @param owner
	 * @param list
	 * @param ante
	 * @param bringIn
	 */
	public Table(UserObject owner, List<Player> list, double ante, double bringIn){
		this.owner = owner;
		this.ante = ante;
		this.bringIn = bringIn;
		this.players = new ArrayList<Player> (list);
	}
	
	//public void newGame
	
	
}
