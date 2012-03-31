package Server.gameModule;

import java.util.ArrayList;

/**
 * A class of hands
 * 
 * @author mouhyi
 */
public class Hand {
	private ArrayList<Card> cards;
	
	/**
	 * Constructor
	 * 
	 * @author mouhyi
	 */
	public Hand(){
		ArrayList<Card> cards = new ArrayList<Card>();
	}
	 /**
	  * Adds new card to the hand
	  * @param card
	  * @author mouhyi
	  */
	public void add(Card card){
		cards.add(card);
	}
	
	/**
	 * Returns the number of cards in this hand
	 * 
	 * @author mouhyi
	 */
	public int getSize(){
		return cards.size();
	}
}
