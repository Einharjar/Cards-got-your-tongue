

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card>currentHand = new ArrayList<Card>();
	
	public Hand(){
	}
	
	public void AddCard(Deck d) {
		currentHand.add(d.draw());
	}
	
	public ArrayList<Card> getHand(){//Ret. ArrayList av Card
		return currentHand;
	}
	
	
	
}