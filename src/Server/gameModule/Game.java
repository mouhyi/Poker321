package Server.gameModule;

import java.util.ArrayList;
import java.util.Collections;
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
	public synchronized void removePlayer(int userId) {
		Player player = this.getPlayer(userId);
		if (player != null) {
			players.remove(player);
		}
	}

	/**
	 * Updates the game state whith a player calling the current bet
	 * 
	 * @param userId
	 *            - userId of the player making the bet player
	 * @return
	 */
	public synchronized int call(int userId) {
		Player player = this.getPlayer(userId);
		return (player == null) ? -1 : player.bet(curBet);
	}

	/**
	 * Updates the game state whith a player raising the current bet
	 * 
	 * @param userId
	 *            - userId of the player making the bet player
	 * @param bet
	 *            - value of the raise
	 * @return 0 on success, -1 on failure
	 */
	public synchronized int raise(int userId, double bet) {
		Player player = this.getPlayer(userId);
		if (player == null) {
			return -1;
		}
		curBet = bet;
		if (player.bet(bet) == 0) {
			curBet = bet;
			return 0;
		}
		return -1;
	}

	/**
	 * Fetches this game's list of players for a specific player
	 * 
	 * @param userId
	 * @return player with corresponding userId or null if user not found
	 * @author mouhyi
	 */
	public synchronized Player getPlayer(int userId) {
		for (Player p : players) {
			if (p.getId() == userId) {
				return p;
			}
		}
		return null;
	}

	// TODO implement doBetting() used in each round
	// TODO getWinner()

	/**
	 * Determines the winner of this game. If there is only one player
	 * remaining, then he is the winner. Otherwise, there is a showdown to
	 * determine the player with the highest hand value
	 * 
	 * @return the winner of this game
	 */
	public synchronized Player getWinner() {
		for (Player p: players){
			p.mergeHand();
		}
		return this.getBestHand();

	}
	
	/**
	 * Determines the player with the best face up hand up to this round
	 * 
	 * @return Player
	 */
	public synchronized Player getBestHand(){
		Collections.sort(players);
		return players.get(players.size()-1);
	}

}
