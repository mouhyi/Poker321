package Server.gameModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the rules of the game
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
	 * @param ante
	 * @param bringIn
	 * @param list
	 */
	public Game(double ante , double bringIn, List<Player> list){
		players = new ArrayList<Player>(list);
		this.ante = ante;
		this.bringIn = bringIn;
		pot =0;
		curBet=0;
		round=0;
		deck = new Deck();
	}
}
