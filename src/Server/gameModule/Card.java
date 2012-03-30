package Server.gameModule;

public class Card {
		
	private Suit suit;
	private Rank rank;
	
	/**
	 * Constructor
	 * 
	 * @author mouhyi
	 */
	Card(Rank rank, Suit suit){
	}
	
	/**
	 * Settors ang gettors
	 * 
	 * @author mouhyi
	 * 
	 */
	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Rank getRank() {
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
	public String toString(){
		
	}
	
	/**
	 * This method overrides Object.equals(Object)
	 * @param obj The object which to compare this Card value. Must be a Card object.
	 * @return true if the cards are equal, false otherwise
	 * 
	 * @author mouhyi
	 */
	@Override
	public boolean equals(Object obj) {
		
	}
	
	/***************************
	 * This method implements the Comparable part of this class.
	 *
	 * @param obj The object which to compare this Card value. Must be a Card object.
	 * @return A negative integer if this object is less than the Card argument; zero if this Card 
	 *         is equal to the Card argument; a positive number if this Card is greater than the 
	 *         Card argument.
	 *@author mouhyi
	 **/
	@Override
	public boolean compareTo(Object obj){
		
	}
}
