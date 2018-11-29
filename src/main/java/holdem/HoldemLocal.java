package holdem;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;




/*
 * TODO
 * fixa så att blinds tas emot
 * fixa så att korten dealas från dealern
 * kolla vem som vinner
 * snacka med websockets
 * 
 */

public class HoldemLocal {
	Scanner reader = new Scanner(System.in); 
	Hand[] players;
	double[] playerPayed;
	int[] playerScore;
	int dealer = 0;
	Hand table;
	Deck deck;
	double bigBlinds= 30;

	public HoldemLocal(int playersInt) {
		players = new Hand[playersInt];
		playerPayed = new double[playersInt];
		playerScore = new int[playersInt];
		players[0] = new Hand();
		deck = new Deck();
		
		
		
		
		
		
		for(int i = 0; i <= playersInt; i++) {
			if(StubMethod("Websocket[i] Open and raise or call")){
				players[i] = new Hand();
				StubMethod("remove blinds from player, add blinds to pot (fold, raise or call)");
				if(StubMethod("if Bigfolds or raise")) {
					StubMethod("set owed (or payed)");
				}
			}
		}

		bettingRound();

		deck.shuffle();
		
		//Player cards dealt
		for (int card = 0; card < 2; card++) {
			for (int i = 0; i < playersInt; i++) {
				if(players[i] != null) {
					players[i].AddCard(deck);
					}
			}
		}

		
		


		
		//The Flop
			table = new Hand();
			for (int j = 0; j < 3; j++) {
				table.AddCard(deck);
			}

			bettingRound();
			
			// The turn
			table.AddCard(deck);	
			


			bettingRound();

			
			
			// The river
			table.AddCard(deck);
			

			bettingRound();

			Showdown();
			

			System.out.println("Table = " +table);
			System.out.println("Pot is "+getPot());
			for (int i = 0; i < players.length; i++) {
				if (players[i] != null) {
					System.out.println(players[i]+" "+i);
					
				}
			}
	}

	public static void main(String[] args) {
		new HoldemLocal(2);
	}
	
	private boolean StubMethod(String s) {
		return false;
	}
	private double getOwed(int player) {
		double largest = 0;
		for (int i = 0; i < players.length; i++) {
			if(playerPayed[i] > largest) {
				largest = playerPayed[i];
			}
		}
		return Math.max(largest-playerPayed[player], 0);
	}
	private double getPot() {
		double amount = 0;
		for (int i = 0; i < playerPayed.length; i++) {
			
				amount += playerPayed[i];
			
		}
		return amount;
	}
	private int getPlayerResponse(int player){

if(getOwed(player) <= 0) 
	System.out.println("(2):Fold (3):Raise ");
else
System.out.println("(1):Called (2):Fold (3):Raise ");
int n = reader.nextInt(); 
		return n;
	}
	
	private void bettingRound() {
		System.out.println(players.length);
		System.out.println("Table = " +table);
		System.out.println("Pot is "+getPot());
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				System.out.println(players[i]+" "+i);
				
			}
		}
		
		boolean raisedThisRound = true;
		while(raisedThisRound ) {
			raisedThisRound = false;
			
			
			for (int i = 0; i < players.length; i++) {
				if(players[i] != null) {
					int resp = 2;
					if(i == 0)
					resp = getPlayerResponse(i);
				
					if(resp == 1) {
						StubMethod("Payed ++");
					}
					else if(resp == 2) {
						playerPayed[i] += getOwed(i);
					}
					
					else if(resp == 3) {
						playerPayed[i] += getOwed(i)+getRaise(i);
						raisedThisRound = true;
					}
				}
				
			}
			
		}

	
		
	}
	
	private double getRaise(int player){

System.out.println("Raise how much?");
double n = reader.nextDouble(); 
		return n;
	}
	
	private void Showdown() {
		System.out.println("---------------------------------------");
		System.out.println("!!! SHOWDOWN !!!");
		Hand compareHand = new Hand();
for (int hand = 0; hand < players.length; hand++) {
	
	if(players[hand] == null)
		continue;

		for (int j = 0; j < table.getHand().size(); j++) {
			compareHand.AddCard(table.getHand().get(j));
		}
		
			for (int j = 0; j < players[hand].getHand().size(); j++) {
				Card c = players[hand].getHand().get(j);
				compareHand.AddCard(c);
			}
		
//			Collections.sort(compareHand.getHand(),new Comparator<Card>(){
//				
//
//				@Override
//				public int compare(Card o1, Card o2) {
//	int retInt = 0;
//	if(o1.getValue()>o2.getValue())
//	retInt = -1;
//	else if (o1.getValue()==o2.getValue())
//		retInt = 0;
//	else
//		retInt = 1;
//
//	return retInt;
//				}
//				 });
//			
		
//		System.out.println(
				findCombos(compareHand, hand);
//				);

		
	}
//		System.out.println("---------------------------------------");
	}
	
	
	public void findCombos(Hand h, int player) {
		playerScore[player] = 0;

//		System.out.println(h.getHand().size());
		String retStr = null;
		int handStrength = 0;
		

			Hand c = new Hand();
			c.AddCard(new Card(0,Suits.HEARTS));
			c.AddCard(new Card(0,Suits.HEARTS));
			c.AddCard(new Card(0,Suits.HEARTS));
			c.AddCard(new Card(0,Suits.HEARTS));
			c.AddCard(new Card(0,Suits.HEARTS));

			for (int i1 = 0; i1 < 7; i1++) {
				Card c1 = h.getHand().get(i1);
				c.getHand().ensureCapacity(5);
				c.getHand().set(0, c1);
				for (int i2 = 0; i2 < 7; i2++) {
					
					Card c2 = h.getHand().get(i2);
					if(c2.equals(c1))
						continue;
					c.getHand().set(1, c2);
					
					for (int i3 = 0; i3 < 7; i3++) {

						Card c3 = h.getHand().get(i3);
						if(c3.equals(c1) || c3.equals(c2))
							continue;
						c.getHand().set(2, c3);
						
						for (int i4 = 0; i4 < 7; i4++) {

							Card c4 = h.getHand().get(i4);
							if(c4.equals(c1) || c4.equals(c2) || c4.equals(c3))
								continue;
							c.getHand().set(3, c4);
							
							for (int i5 = 0; i5 < 7; i5++) {
								

								Card c5 = h.getHand().get(i5);
								if(c5.equals(c1) || c5.equals(c2) || c5.equals(c3) || c5.equals(c4))
									continue;
								c.getHand().set(4, c5);
								int thisScore = EvaluateHand(c);
if(thisScore > playerScore[player]){
	System.out.println(thisScore);
	playerScore[player] = thisScore;
	System.out.println(c);
	switch(thisScore) {
	case(100):
		System.out.println("Royal");
	break;
	case(90):
		System.out.println("straight flush");
	break;
	case(80):
		System.out.println("four of a kind");
	break;
	case(70):
		System.out.println("fullhouse");
	break;
	case(60):
		System.out.println("flush");
	break;
	case(50):
		System.out.println("straight");
	break;
	case(40):
		System.out.println("three of a kind");
	break;
	case(30):
		System.out.println("two pair");
	break;
	case(20):
		System.out.println("one pair");
	break;
	default:
		System.out.println("high card");
	break;
	}
}
								
							}								
							}								
							}								
							}
			}
			
			
			
			
			
			
			
			
			
			
			
//			for (int i = 0; i < h.getHand().size(); i++) {
////				System.out.println("i="+i);
//				Card card = h.getHand().get(i);
//				c[0] = card;
//				for (int j = 0; j < h.getHand().size(); j++) {
////					System.out.println("j="+j);
//					for (int j2 = 1; j2 < 5; j2++) {
//						int cardNo = j2+j-1;
//						if(cardNo >= i)
//							cardNo++;
//						if(cardNo >= 7)
//							cardNo = (cardNo-7);
//						c[j2] = h.getHand().get(cardNo);
////						System.out.println("j2="+j2);
////						System.out.println("cardNo="+cardNo);
//							
//						
//					}
//
//
//					for (Card cardo : c) {
//						System.out.print(cardo+" ");
//					}	
//					System.out.println();
//				}
//				
//			}
			
			
			
			
			
			
				
//				return retStr;
	
	}
	

		public int EvaluateHand(Hand h) {
int handRating = 0;
Hand hand = new Hand();
hand.getHand().addAll(h.getHand());
			Collections.sort(hand.getHand(),new Comparator<Card>(){
			
			@Override
			public int compare(Card o1, Card o2) {
int retInt = 0;
if(o1.getValue()>o2.getValue())
retInt = -1;
else if (o1.getValue()==o2.getValue())
	retInt = 0;
else
	retInt = 1;

return retInt;
			}
			 });
			

			boolean onePair = false;
			boolean twoPair = false;
			boolean fullHouse = false;
			boolean threeOfAKind = false;
			boolean fourOfAKind = false;

			HashMap<Integer, Integer> duplicates = new HashMap<Integer, Integer>();
			for(Card c1 : hand.getHand()) {
				for (Card c2 : hand.getHand()) {
					if(c1.equals(c2))
						continue;
					if(c1.getValue() == c2.getValue())
						duplicates.put(c1.getValue(), duplicates.get(c1.getValue()) == null ? 1 : duplicates.get(c1.getValue())+1);
					
				}
			}
			for ( Integer i : duplicates.keySet()) {
//				System.out.println(duplicates.get(i) +", "+i);
				if(duplicates.get(i) == 13)
					fourOfAKind = true;
				if(duplicates.get(i) == 6)
					threeOfAKind = true;
				if(duplicates.get(i) == 2 && onePair == false)
					onePair = true;
				else if(duplicates.get(i) == 2 && onePair == true ) {
//					onePair = false;
					twoPair = true;
				}
				if(duplicates.get(i) == 6 && onePair == true ) {
					threeOfAKind = true;
					fullHouse = true;
				}
			}

			int highestVal = h.getHand().get(0).getValue();
			int lowestVal = h.getHand().get(4).getValue();
			
			if(h.getHand().get(0).getValue() == 13
					&& h.getHand().get(1).getValue() == 12
					&& h.getHand().get(2).getValue() == 11
					&& h.getHand().get(3).getValue() == 10
					&& h.getHand().get(4).getValue() == 1

							&& h.getHand().get(0).getSuit() == h.getHand().get(1).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(2).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(3).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(4).getSuit()) {
//				System.out.println(hand);
//				System.out.println("Royal");
				handRating = 100;
			}

			else if(h.getHand().get(0).getValue() == h.getHand().get(4).getValue()+4
					&& h.getHand().get(1).getValue() == h.getHand().get(4).getValue()+3
					&& h.getHand().get(2).getValue() == h.getHand().get(4).getValue()+2
					&& h.getHand().get(3).getValue() == h.getHand().get(4).getValue()+1
					&& h.getHand().get(4).getValue() == 1 
					

							&& h.getHand().get(0).getSuit() == h.getHand().get(1).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(2).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(3).getSuit()
							&& h.getHand().get(0).getSuit() == h.getHand().get(4).getSuit()
					) {
//				System.out.println(hand);
//				System.out.println("Straight flush ");
				handRating = 90;
			}
			else if(fourOfAKind) {
//				System.out.println(hand);
//				System.out.println("four of a kind");
				handRating = 80;
			}
			else if(fullHouse) {
//				System.out.println(hand);
//				System.out.println("fullhouse");
				handRating = 70;
			}
			else if( h.getHand().get(0).getSuit() == h.getHand().get(1).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(2).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(3).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(4).getSuit()) {
//				System.out.println(hand);
//				System.out.println("flush");
				handRating = 60;
			}
			else if(h.getHand().get(0).getValue() == lowestVal+4
					&& h.getHand().get(1).getValue() == lowestVal+3
					&& h.getHand().get(2).getValue() == lowestVal+2
					&& h.getHand().get(3).getValue() == lowestVal+1
					&& h.getHand().get(4).getValue() == lowestVal) {
//				System.out.println(hand);
//				System.out.println("Straight");
				handRating = 50;
			}
			else if(threeOfAKind) {
//				System.out.println(hand);
//				System.out.println("three of a kind");
				handRating = 40;
			}
			else if(twoPair) {
//				System.out.println(hand);
//				System.out.println("two pair");
				handRating = 30;
			}
			else if(onePair) {
//				System.out.println(hand);
//				System.out.println("one pair");
				handRating = 20;
				
			}
			else {
				if(lowestVal == 1)
					handRating = 15;
				else
					handRating = highestVal;
					
			}
			return handRating;
		}

}
