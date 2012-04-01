package Server.gameModule;

/**
 * Class for simple testings
 * @author mouhyi
 *
 */
public class Test {
	
	public static void main(String[] args){
		//testCardComp();
		testDeck();
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
}
