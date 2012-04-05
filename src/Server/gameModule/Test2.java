package Server.gameModule;

import java.util.ArrayList;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testHands5();
		//clearDeckTest();
	}
	public static void clearDeckTest(){

		//Deck uses arrayList which should be quite stable
		//this draws every card +1 in the deck and see how it responds
		Deck test = new Deck();
		for(int i =0;i<52;i++){
			Card current = test.drawCard();
			System.out.println(current.toString());

		}
		System.out.println(test.getSize()==0);//should still be zero
	}
	public static void testHands5(){
		//mainly tests on two things
		//1. self vs self (same hand type)
		//2. self vs higher
		//3. self vs lower
		//just making sure all the different combinations of the code is working well
		//if ran correctly it should give no zeros and 
		//	if i>j, then all tests on i against j should be positive 
		
		//sorry that the code is kinda messy since i screwed up the hand ordering a few times ...
		
		//straight_flush 1  
		Hand all[] = new Hand[50]; 
		Hand sf1 = new Hand();
		ArrayList<Card> list = new ArrayList<Card> (5);
		list.add(new Card(Rank.Jack, Suit.Clubs));
		list.add(new Card(Rank.Ten, Suit.Clubs));
		list.add(new Card(Rank.Nine, Suit.Clubs));
		list.add(new Card(Rank.Eight, Suit.Clubs));
		list.add(new Card(Rank.Seven, Suit.Clubs));
		sf1.addAll(list);
		all[0]=sf1;
		//straight_flush2
		Hand sf2 = new Hand();
		ArrayList<Card> list2 = new ArrayList<Card> (5);
		list2.add(new Card(Rank.Jack, Suit.Hearts));
		list2.add(new Card(Rank.Ten, Suit.Hearts));
		list2.add(new Card(Rank.Nine, Suit.Hearts));
		list2.add(new Card(Rank.Eight, Suit.Hearts));
		list2.add(new Card(Rank.Seven, Suit.Hearts));
		sf2.addAll(list2);
		all[1]=sf2;
		
		//Ace_straight_flush 
		//Special case since its supposed to be smaller then all the other straight flushes
		Hand sfa = new Hand();
		ArrayList<Card> listA = new ArrayList<Card> (5);
		listA.add(new Card(Rank.Jack, Suit.Hearts));
		listA.add(new Card(Rank.Ten, Suit.Hearts));
		listA.add(new Card(Rank.Nine, Suit.Hearts));
		listA.add(new Card(Rank.Eight, Suit.Hearts));
		listA.add(new Card(Rank.Seven, Suit.Hearts));
		sfa.addAll(listA);
		all[2]=sfa;

		//four of a kind
		Hand fo1= new Hand();
		ArrayList<Card> list7 = new ArrayList<Card>(5);
		list7.add(new Card(Rank.Four, Suit.Hearts));
		list7.add(new Card(Rank.Four, Suit.Clubs));
		list7.add(new Card(Rank.Four, Suit.Spades));
		list7.add(new Card(Rank.Four, Suit.Diamonds));
		list7.add(new Card(Rank.Nine, Suit.Diamonds));
		fo1.addAll(list7);
		all[3]=fo1;
		Hand fo2= new Hand();
		ArrayList<Card> list8 = new ArrayList<Card>(5);
		list8.add(new Card(Rank.Three, Suit.Hearts));
		list8.add(new Card(Rank.Three, Suit.Clubs));
		list8.add(new Card(Rank.Three, Suit.Spades));
		list8.add(new Card(Rank.Three, Suit.Diamonds));
		list8.add(new Card(Rank.Six, Suit.Clubs));
		fo2.addAll(list8);
		all[4]=fo2;
		
		//full house
		Hand fh1= new Hand();
		ArrayList<Card> listFH = new ArrayList<Card>(5);
		listFH.add(new Card(Rank.Eight, Suit.Hearts));
		listFH.add(new Card(Rank.Eight, Suit.Clubs));
		listFH.add(new Card(Rank.Eight, Suit.Spades));
		listFH.add(new Card(Rank.Nine, Suit.Clubs));
		listFH.add(new Card(Rank.Nine, Suit.Diamonds));
		fh1.addAll(listFH);
		all[5]=fh1;
		Hand fh2= new Hand();
		ArrayList<Card> listFH2 = new ArrayList<Card>(5);
		listFH2.add(new Card(Rank.Seven, Suit.Hearts));
		listFH2.add(new Card(Rank.Seven, Suit.Clubs));
		listFH2.add(new Card(Rank.Seven, Suit.Spades));
		listFH2.add(new Card(Rank.Nine, Suit.Spades));
		listFH2.add(new Card(Rank.Nine, Suit.Hearts));
		fh2.addAll(listFH2);
		all[6]=fh2;
		
		
		//flush
		Hand f1 = new Hand();
		ArrayList<Card> list3 = new ArrayList<Card> (5);
		list3.add(new Card(Rank.Deuce, Suit.Hearts));
		list3.add(new Card(Rank.Jack, Suit.Hearts));
		list3.add(new Card(Rank.Nine, Suit.Hearts));
		list3.add(new Card(Rank.King, Suit.Hearts));
		list3.add(new Card(Rank.Queen, Suit.Hearts));
		f1.addAll(list3);
		all[7]=f1;

		//flush2 - it is supposed to be smaller then flush
		Hand f2 = new Hand();
		ArrayList<Card> list4 = new ArrayList<Card> (5);
		list4.add(new Card(Rank.Ten, Suit.Clubs));
		list4.add(new Card(Rank.Five, Suit.Clubs));
		list4.add(new Card(Rank.Nine, Suit.Clubs));
		list4.add(new Card(Rank.Jack, Suit.Clubs));
		list4.add(new Card(Rank.Queen, Suit.Clubs));
		f2.addAll(list4);
		all[8]=f2;

		//straights
		Hand s1= new Hand();
		ArrayList<Card> list5 = new ArrayList<Card>(5);
		list5.add(new Card(Rank.Ten, Suit.Hearts));
		list5.add(new Card(Rank.Jack, Suit.Clubs));
		list5.add(new Card(Rank.Seven, Suit.Clubs));
		list5.add(new Card(Rank.Eight, Suit.Clubs));
		list5.add(new Card(Rank.Nine, Suit.Clubs));
		s1.addAll(list5);
		all[9]=s1;
		Hand s2= new Hand();
		ArrayList<Card> list6 = new ArrayList<Card>(5);
		list6.add(new Card(Rank.Ten, Suit.Clubs));
		list6.add(new Card(Rank.Jack, Suit.Spades));
		list6.add(new Card(Rank.Seven, Suit.Spades));
		list6.add(new Card(Rank.Eight, Suit.Diamonds));
		list6.add(new Card(Rank.Nine, Suit.Diamonds));
		s2.addAll(list6);
		all[10]=s2;
		
		//three of a kind
		Hand to1= new Hand();
		ArrayList<Card> list9 = new ArrayList<Card>(5);
		list9.add(new Card(Rank.Three, Suit.Hearts));
		list9.add(new Card(Rank.Three, Suit.Clubs));
		list9.add(new Card(Rank.Three, Suit.Spades));
		list9.add(new Card(Rank.Deuce, Suit.Diamonds));
		list9.add(new Card(Rank.Six, Suit.Clubs));
		to1.addAll(list9);
		all[11]=to1;
		Hand to2= new Hand();
		ArrayList<Card> list10 = new ArrayList<Card>(5);
		list10.add(new Card(Rank.Deuce, Suit.Hearts));
		list10.add(new Card(Rank.Deuce, Suit.Clubs));
		list10.add(new Card(Rank.Deuce, Suit.Spades));
		list10.add(new Card(Rank.Three, Suit.Diamonds));
		list10.add(new Card(Rank.Six, Suit.Spades));
		to2.addAll(list10);
		all[12]=to2;

		//double pair
		Hand dp1= new Hand();
		ArrayList<Card> list11 = new ArrayList<Card>(5);
		list11.add(new Card(Rank.Eight, Suit.Hearts));
		list11.add(new Card(Rank.Eight, Suit.Clubs));
		list11.add(new Card(Rank.Seven, Suit.Spades));
		list11.add(new Card(Rank.Seven, Suit.Diamonds));
		list11.add(new Card(Rank.Deuce, Suit.Clubs));
		dp1.addAll(list11);
		all[13]=dp1;

		Hand dp2= new Hand();
		ArrayList<Card> list12 = new ArrayList<Card>(5);
		list12.add(new Card(Rank.Seven, Suit.Hearts));
		list12.add(new Card(Rank.Seven, Suit.Clubs));
		list12.add(new Card(Rank.Eight, Suit.Spades));
		list12.add(new Card(Rank.Eight, Suit.Diamonds));
		list12.add(new Card(Rank.Deuce, Suit.Spades));
		dp2.addAll(list12);
		all[14]=dp2;

		//pair
		Hand sp1= new Hand();
		ArrayList<Card> list13 = new ArrayList<Card>(5);
		list13.add(new Card(Rank.Five, Suit.Hearts));
		list13.add(new Card(Rank.Five, Suit.Clubs));
		list13.add(new Card(Rank.Six, Suit.Spades));
		list13.add(new Card(Rank.Seven, Suit.Diamonds));
		list13.add(new Card(Rank.Ace, Suit.Clubs));
		sp1.addAll(list13);
		all[15]=sp1;

		Hand sp2= new Hand();
		ArrayList<Card> list14 = new ArrayList<Card>(5);
		list14.add(new Card(Rank.Four, Suit.Hearts));
		list14.add(new Card(Rank.Four, Suit.Clubs));
		list14.add(new Card(Rank.Three, Suit.Spades));
		list14.add(new Card(Rank.Six, Suit.Diamonds));
		list14.add(new Card(Rank.Ace, Suit.Spades));
		sp2.addAll(list14);
		all[16]=sp2;

		//high card
		//Ace of spades is highest
		Hand hc1= new Hand();
		ArrayList<Card> list15 = new ArrayList<Card>(5);
		list15.add(new Card(Rank.Deuce, Suit.Hearts));
		list15.add(new Card(Rank.Five, Suit.Clubs));
		list15.add(new Card(Rank.Six, Suit.Spades));
		list15.add(new Card(Rank.Seven, Suit.Diamonds));
		list15.add(new Card(Rank.Ace, Suit.Spades));
		hc1.addAll(list15);
		all[17]=hc1;
		
		Hand hc2= new Hand();
		ArrayList<Card> list16 = new ArrayList<Card>(5);
		list16.add(new Card(Rank.Four, Suit.Hearts));
		list16.add(new Card(Rank.Nine, Suit.Clubs));
		list16.add(new Card(Rank.Three, Suit.Spades));
		list16.add(new Card(Rank.Six, Suit.Diamonds));
		list16.add(new Card(Rank.Ace, Suit.Diamonds));
		hc2.addAll(list16);
		all[18]=hc2;

		for(int i =0;i<19;i++){
			System.out.println("Hand" + i+ " :");
			for(int j=0;j<19;j++){
				if(i!=j){
					System.out.println("\t vs hand "+j+" :"+all[i].compareTo(all[j]));}

			}


		}
	}
}

