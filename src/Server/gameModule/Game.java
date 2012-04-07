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

	public final int ROUNDS = 4;

	// player's seat = player's index in the ArrayList
	private ArrayList<Player> players;
	int round;
	ArrayList<Pot> pots;
	double curBet;
	Deck deck;
	double ante;
	double bringIn;
	// index of current player in the arrayList
	int curPlayer;
	// If everybody left is all in
	private boolean allIn;

	/**
	 * Wrapper class for poker pots added to handle side pots in all-in
	 * situations
	 * 
	 * @author mouhyi
	 * 
	 */
	private class Pot {
		double chips;
		ArrayList<Player> players;

		private Pot(double chips, ArrayList<Player> players) {
			this.chips = chips;
			this.players = players;
		}
	}

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
		curBet = 0;
		curPlayer = 0;
		round = 1;
		deck = new Deck();
		pots.add(new Pot(0.0, players));
	}

	/**
	 * Runs a five card stud Game
	 */
	public synchronized void play(){
		// ante & first round
		collectAnte();
		round =1;
		curPlayer = doFirstRound();
		players.get(curPlayer).bet(bringIn);
		curPlayer = getNextPlayer();
		doBetting(1);
		
		while(round < ROUNDS){
			if(allIn){
				allInShowdown();
				break;
			}
			doBetting(0);
		}
		// notify players of the winners, ammount won
		dividePot();
	}

	public int getNextPlayer() {
		return (curPlayer + 1) % players.size();
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
	 * Updates the game state with a player calling the current bet
	 * 
	 * @param userId
	 *            - userId of the player making the bet
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
	// TODO create All-in Buuton
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
	 * Flag the game when a player goes all in
	 * 
	 * @param userId
	 *            - player all in
	 */
	public synchronized void allIn(int userId) {
		allIn = true;
		curBet = getPlayer(userId).getChips();
	}

	public synchronized void allInShowdown() {
		for (int i = round + 1; i <= ROUNDS; i++) {
			this.deal();
		}
	}

	/**
	 * Should be called at the end of each betting round to update players chips
	 * and add the bets to the pot
	 */
	public synchronized void confirmBet() {
		for (Player p : players) {
			p.bet(curBet);
			p.confirmBet();
		}
		pots.get(0).chips += curBet * players.size();
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

	// TODO implement doBetting() used in each round & Game Controller (RMI
	// callbacks)

	/**
	 * Divides the pot between the winners
	 * 
	 * @author mouhyi
	 */
	public synchronized void dividePot() {
		ArrayList<Player> winners = this.getWinner();
		for (Player p : winners) {
			p.addChips(pots.get(0).chips / winners.size());
		}
	}

	/**
	 * Determines the winner(s) of this game. If there is only one player
	 * remaining, then he is the winner. Otherwise, there is a showdown to
	 * determine the player with the highest hand value. This method should be
	 * called only at the end of the game
	 * 
	 * @return the winner of this game
	 * @author mouhyi
	 */
	public synchronized ArrayList<Player> getWinner() {
		for (Player p : players) {
			p.mergeHand();
		}
		return this.getBestHand();

	}

	/**
	 * Determines the player(s) with the best face up hand in this round
	 * 
	 * @return ArrayList<Player>
	 * @author mouhyi
	 */
	public synchronized ArrayList<Player> getBestHand() {
		ArrayList<Player> playersCpy = new ArrayList<Player>(players);
		Collections.sort(playersCpy);
		ArrayList<Player> best = new ArrayList<Player>();
		Player bestPlayer = playersCpy.get(playersCpy.size() - 1);
		int i = playersCpy.size() - 1;
		while (i >= 0 && playersCpy.get(i) == bestPlayer) {
			best.add(playersCpy.get(i));
		}
		return best;
	}

	/**
	 * Determines the index of the first player to bet(highest hand) in the
	 * current round. If there is a tie, the player with lowest seat number is
	 * returned Must be called at the beginning of each round.
	 * 
	 * @return the player betting first
	 * @author mouhyi
	 */
	public synchronized int firstToBet() {
		ArrayList<Player> best = this.getBestHand();
		int minSeat = -1;
		for (Player p : best) {
			if (players.indexOf(p) < minSeat) {
				minSeat = players.indexOf(p);

			}
		}
		return minSeat;
	}

	/**
	 * Collect the ante and puts it in the main pot
	 * 
	 * @author mouhyi
	 */
	public synchronized void collectAnte() {
		for (Player p : players) {
			p.bet(ante);
			p.confirmBet();
			pots.get(0).chips += ante;
		}
	}

	/**
	 * Each player is dealt one card face down, followed by one card face up
	 * Then the player with the lowest-ranking upcard must pay the bring in.
	 * 
	 * @return minSeat - index of player with lowest up card
	 * @author mouhyi
	 */
	public synchronized int doFirstRound() {
		for (Player p : players) {
			p.getCard(deck.drawCard(), true);
		}
		Player minPlayer = null;
		Card minCard = null;
		for (Player p : players) {
			Card tmp = deck.drawCard();
			p.getCard(tmp, false);
			if ((minCard == null) || (minCard.compareBySuit(tmp) > 0)) {
				minCard = tmp;
				minPlayer = p;
			}
		}
		return players.indexOf(minPlayer);
		// minPlayer bets >= bringIn

	}

	/**
	 * Deals a card to each player in the game
	 * 
	 * @author mouhyi
	 * 
	 */
	public synchronized void deal() {
		for (Player p : players) {
			p.getCard(deck.drawCard(), false);
		}
	}

	/**
	 * 
	 * @param count
	 *            - number of calls to the last bet
	 */
	public synchronized void doBetting(int count) {

		// betting goes in increasing indices and wraps around
		// chips are added to pot at the end in confirmBet
		// or immedialtely if a player folds
		/*
		 * notify curplayer: if player folds(or timedout) remove him from players
		 * and confirm his bet and update pot
		 * else curBet = player's bet (error if bet<curBet)
		 * count++ for call, count=1 for a raise
	 	 * repeat until count = players.size or all-in=true
	 	 * confirmBet
		 */
	}

}
