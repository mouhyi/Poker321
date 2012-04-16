package Server.gameModule;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Remote.ICard;
import Remote.Rank;
import Remote.Suit;

/**
 * Wrapper class for poker cards
 * 
 * @author mouhyi
 *
 */

// all methods tested on Mar 31, 03:21pm
public class Card extends UnicastRemoteObject implements Comparable<Card>, ICard {

	private Suit suit;
	private Rank rank;
	boolean up;

	/**
	 * Constructor
	 * 
	 * @author mouhyi
	 */
	public Card(Rank rank, Suit suit) throws RemoteException {
		this.rank = rank;
		this.suit = suit;
		up = true;
	}

	/*
	 * Settors ang gettors 
	 * TODO: delete setters: a card instance should be final
	 * @author mouhyi 
	 */
	public void setDown(){
		this.up = false;
	}
	
	public Suit getSuit() throws RemoteException {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Rank getRank() throws RemoteException {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * this method Creates a string representation of the card.
	 * 
	 * @author mouhyi
	 */
	public String toString() {
		return rank + " of " + suit;
	}

	/**
	 * This method overrides Object.equals(Object)
	 * 
	 * @param obj
	 *            The object which to compare this Card value. Must be a Card
	 *            object.
	 * @return true if the cards are equal, false otherwise
	 * @author mouhyi
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Card)
			return (this.rank == ((Card) obj).rank);
		return false;
	}

	/***************************
	 * This method implements the Comparable part of this class. IT defines a
	 * natural ordering of cards based on its rank
	 * 
	 * @param c: The Card which to compare this Card value.
	 * @return A negative integer if this card is less than the Card argument;
	 *         zero if this Card is equal to the Card argument;
	 *         a positive number if this Card is greater than the Card argument.
	 * @author mouhyi
	 **/
	public int compareTo(Card c) {
		return (this.rank.compareTo(c.rank));

	}

	/**
	 * Compare two cards based on their rank and use suits as tie breaker in the following order: clubs
	 * (lowest),diamonds, hearts, and spades (highest).
	 * 
	 * @param c: Card object
	 * @return int
	 * @author mouhyi
	 */
	public int compareBySuit(Card c) {
		int tmp = this.rank.compareTo(c.rank);
		return (tmp != 0) ? tmp : (this.suit.compareTo(c.suit)) ;
	}
}
