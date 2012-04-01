package Server.gameModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * A class of hands
 * TODO: Algorithm to Determine the value of the hand
 * 
 * @author mouhyi
 */
public class Hand implements Comparable<Hand> {
	private ArrayList<Card> cards;
	
	/**
	 * Constructor
	 * 
	 * @author mouhyi
	 */
	public Hand(){
		// ArrayList with initial capacity = 5 = size of a poker hand;
		ArrayList<Card> cards = new ArrayList<Card>(5);
	}
	 /**
	  * Adds new card to this hand
	  * 
	  * @param card
	  * @author mouhyi
	  */
	public void add(Card card){
		cards.add(card);
	}
	
	/**
	  * Removes a card from this hand
	  * 
	  * @param card
	  * @author mouhyi
	  */
	public void remove(Card card){
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
	
	// Test All the following methods
	
	/**
	 * Determines the value of this hand.
	 * notes: If there is a flush, there cannot be pairings and vice versa.
	 * If there is a pairing, there cannot be straight and vice versa.
	 * 
	 * @return the value of this hand
	 * @author mouhyi
	 */
	public HandType getValue(){
		if(isStraightFlush())
			return HandType.STRAIGHT_FLUSH;
		if(isFlush())
			return HandType.FLUSH;
		if(isStraight())
			return HandType.STRAIGHT;
		return this.getPairing();
	}
	
	/**
	 * Test if all the cards in this hand are of the same suit.
	 * 
	 * @return Boolean: true if flush, false if not
	 * @author mouhyi
	 */
	public boolean isFlush(){
		Suit s = cards.get(0).getSuit();
		for (int i=1; i<=5; i++){
			if(cards.get(i).getSuit() != s)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Test if this hand contains five cards in sequence,
	 * 
	 * @return Boolean: true if flush, false if not
	 * @author mouhyi
	 */
	public boolean isStraight(){
		Collections.sort(cards);
		// if hand does not contain an Ace: check the diff between highest and lowest card
		if(cards.get(4).getRank() != Rank.Ace){
			int diff = cards.get(4).getRank().ordinal() - cards.get(0).getRank().ordinal();
			return (diff == 4);
		}
		// if hand contains Ace (at index 4)
		else{
			int diff = cards.get(3).getRank().ordinal() - cards.get(0).getRank().ordinal();
			if(diff !=3) return false;
			return (cards.get(0).getRank() == Rank.Deuce);
		}
	}
	
	/**
	 * Test if this hand contains five cards in sequence, all of the same Suit
	 * 
	 * @return Boolean: true if StraightFlush, false if not
	 * @author mouhyi
	 */
	public boolean isStraightFlush(){
		return (isStraight() && isFlush());
	}
	
	/**
	 * Determine the pairing category of this hand
	 * Call this method if this hand is not Flush nor Straight
	 * 
	 * @return HandType
	 * @author mouhyi
	 */
	public HandType getPairing(){
		HashMap<Rank,Integer> table = new HashMap<Rank,Integer>(13);
		for(Rank rank : Rank.values()){
			table.put(rank, 0);
		}
		for (Card c: cards){
			Rank r = c.getRank();
			int freq = table.get(r);
			table.put(r, freq+1);
		}
		if(table.containsValue(4))
			return HandType.FOUR_OF_A_KIND;
		if(table.containsValue(3) && table.containsValue(2))
			return HandType.FULL_HOUSE;
		if(table.containsValue(3))
			return HandType.THREE_OF_A_KIND;
		if(table.containsValue(2)){
			Collection<Integer> col = table.values();
			int freq =0;
			for (int i: col){
				if (i==2) freq++;
			}
			if(freq==2)
				return HandType.TWO_PAIR;
			else
				return HandType.ONE_PAIR;
		}
		return HandType.HIGH_CARD;
	}
	
	/**
	 * Implement the comparable interface part.
	 * IT defines a natural ordering of hands.
	 * 
	 * @param h: Hand
	 * @return A negative integer if this hand is less than the Hand argument;
	 *         zero if this Hand is equal to the Hand argument;
	 *         a positive number if this Hand is greater than the Hand argument.
	 * @author mouhyi
	 */
	public int compareTo(Hand h){
		return ( this.getValue().compareTo(h.getValue()) );
	}
	
	// TODO: implement Tie breaker
	
	/**
	 * Helper method to compare two set of cards based on highest individual card
	 * 
	 * @author mouhyi
	 */
	public static int breakTieHighCard(ArrayList<Card> c1, ArrayList<Card> c2 ){
		Collections.sort(c1);
		Collections.sort(c2);
		int i = c1.size()-1;
		while(i>=0){
			if (c1.get(i) != c2.get(i))
				break;
			i--;
		}
		return c1.get(i).compareTo(c2.get(i));
	}
}
