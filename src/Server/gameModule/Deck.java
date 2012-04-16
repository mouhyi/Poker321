package Server.gameModule;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Remote.Rank;
import Remote.Suit;

/**
 * Wrapper class for a standard deck of cards
 * 
 * @author mouhyi
 *
 */
//all methods tested on Mar 31, 04:11pm

public class Deck {

	private ArrayList<Card> cards;
	
	/**
	 * constructor: : Initialize a standard 52 cards deck without the Jokers
	 * 
	 * @author mouhyi
	 **/
	public Deck(){
		cards= new ArrayList<Card>();
		// initialize the deck with cards
		 for (Suit suit : Suit.values()){
	            for (Rank rank : Rank.values()){
	                try {
						cards.add(new Card(rank, suit));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
		 }
		 // shuffle
		 this.shuffle();
	}

	/**
	 * Draw the card on the top of this deck.
	 * 
	 * @post The second card in the deck becomes the new top card
	 * @return The Card a the top of the deck.
	 * @author mouhyi
	 */
	public synchronized Card drawCard() {
		return cards.remove(cards.size()-1);
	}
	/**
	 * Shuffles the deck
	 * 
	 * @author mouhyi
	 */
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	/**
	 * Get set of cards in this Deck
	 * 
	 * @return ArrayList<Card>
	 * @author mouhyi
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	/**
	 * Get the number of cards in the Deck
	 * 
	 * @return deck size
	 * @author mouhyi
	 */
	public int getSize(){
		return cards.size();
	}
	
	/**
	 * Add a specific card to the deck.
	 * 
	 * @author mouhyi
	 */
	public void addCard(Card card) {
		cards.add(card);
	}
}
