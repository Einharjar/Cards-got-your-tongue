package holdem;


import java.util.ArrayList;

public class Hand {
	@Override
	public String toString() {
		return "Hand [currentHand=" + currentHand + "]";
	}

	private ArrayList<Card>currentHand = new ArrayList<Card>();
	
	public Hand(){
	}

	public void AddCard(Deck d) {
		currentHand.add(d.draw());
	}

	public void AddCard(Card c) {
		currentHand.add(c);
	}
	
	public ArrayList<Card> getHand(){//Ret. ArrayList av Card
		return currentHand;
	}
	
	
	
}