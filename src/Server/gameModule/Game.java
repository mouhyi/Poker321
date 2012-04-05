package Server.gameModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the rules of the game
 * 
 * @author mouhyi
 * 
 */
public class Game {

	private ArrayList<Player> players;
	int round;
	double pot;
	double curBet;
	Deck deck;
	double ante;
	double bringIn;
	int curPlayer;

	/**
	 * Constructor
	 * 
	 * @param ante
	 * @param bringIn
	 * @param list
	 */
	public Game(double ante, double bringIn, List<Player> list) {
		players = new ArrayList<Player>(list);
		this.ante = ante;
		this.bringIn = bringIn;
		pot = 0;
		curBet = 0;
		round = 0;
		deck = new Deck();
	}

	/**
	 * deletes a player from this game. Should be called when a player folds out
	 * or he has been inactive for a long period specified in this class
	 * 
	 * @param player
	 *            - player to be removed
	 * @author mouhyi
	 */
	public synchronized void removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}

}
