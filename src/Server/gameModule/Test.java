package Server.gameModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for simple testings
 * @author mouhyi
 *
 */
public class Test {
	
	public static void main(String[] args){
		//testCardComp();
		//testDeck();
		//testHandgetV();
		//testHandComp();
		//testSortCards();
		testcompHands();
	}
	
	public static void testCardComp(){
		Card c1 = new Card(Rank.Four, Suit.Diamonds);
		Card c2 = new Card(Rank.Jack, Suit.Spades); 
		Card c3 = new Card(Rank.Jack, Suit.Spades);
		System.out.println(c1.compareTo(c2));
		System.out.println(c1.equals(c2));
		System.out.println(c2.compareBySuit(c1));
		System.out.println(c3.equals(c2));
		System.out.println(c3.compareTo(c2));
		System.out.println(c1.toString());
	}
	public static void testDeck(){
		Deck d = new Deck();
		d.shuffle();
		System.out.println(d.drawCard());
		System.out.println(d.getSize());
	}
	
	public static void testHandgetV(){
		Hand h = new Hand();
		ArrayList<Card> list = new ArrayList<Card> (5);
		list.add(new Card(Rank.Ace, Suit.Clubs));
		list.add(new Card(Rank.Four, Suit.Hearts));
		list.add(new Card(Rank.Three, Suit.Diamonds));
		list.add(new Card(Rank.Jack, Suit.Clubs));
		list.add(new Card(Rank.Five, Suit.Hearts));
		
		h.addAll(list);
		System.out.println(h.getValue());
		
	}
	
	public static void testHandComp(){
		Hand h = new Hand();
		ArrayList<Card> list = new ArrayList<Card> (5);
		list.add(new Card(Rank.Jack, Suit.Clubs));
	    list.add(new Card(Rank.Ten, Suit.Clubs));
	    list.add(new Card(Rank.Nine, Suit.Clubs));
	    list.add(new Card(Rank.Eight, Suit.Clubs));
	    list.add(new Card(Rank.Seven, Suit.Clubs));
		h.addAll(list);
		
		Hand h2 = new Hand();
		ArrayList<Card> list2 = new ArrayList<Card> (5);
		list2.add(new Card(Rank.Jack, Suit.Hearts));
	    list2.add(new Card(Rank.Ten, Suit.Hearts));
	    list2.add(new Card(Rank.Nine, Suit.Hearts));
	    list2.add(new Card(Rank.Eight, Suit.Hearts));
	    list2.add(new Card(Rank.Seven, Suit.Hearts));
		h2.addAll(list2);
		
		System.out.println(h.getValue());
		System.out.println(h2.getValue());
		//System.out.println(Hand.breakTieHighCard(list, list2));
		System.out.println(h.compareTo(h2));
	}
	
	public static void testSortCards(){
		Hand h = new Hand();
		ArrayList<Card> list = new ArrayList<Card> (5);
		list.add(new Card(Rank.King, Suit.Hearts));
		list.add(new Card(Rank.King, Suit.Hearts));
		list.add(new Card(Rank.Deuce, Suit.Clubs));
		list.add(new Card(Rank.Deuce, Suit.Hearts));
		list.add(new Card(Rank.Deuce, Suit.Spades));
		h.addAll(list);
		
		List<Map.Entry<Rank, Integer>> sorted = h.sortCards();
		System.out.println(sorted);
		System.out.println(h.getHighestTuple());
	}
	
	public static void testcompHands(){
		Hand h = new Hand();
		ArrayList<Card> list = new ArrayList<Card> (5);
		list.add(new Card(Rank.Ace, Suit.Hearts));
		list.add(new Card(Rank.Three, Suit.Spades));
		list.add(new Card(Rank.Five, Suit.Clubs));
		list.add(new Card(Rank.Deuce, Suit.Hearts));
		list.add(new Card(Rank.Four, Suit.Spades));
		h.addAll(list);
		System.out.println(h.getValue());
		
		Hand h2 = new Hand();
		ArrayList<Card> list2 = new ArrayList<Card> (5);
		list2.add(new Card(Rank.Ace, Suit.Clubs));
		list2.add(new Card(Rank.King, Suit.Hearts));
		list2.add(new Card(Rank.Queen, Suit.Diamonds));
		list2.add(new Card(Rank.Jack, Suit.Spades));
		list2.add(new Card(Rank.Ten, Suit.Hearts));
		h2.addAll(list2);
		System.out.println(h2.getValue());
		
		System.out.println(h.compareTo(h2));
	}
}
