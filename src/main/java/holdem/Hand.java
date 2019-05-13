package holdem;


import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Hand {
	@Override
	public String toString() {
//		StringBuilder sb = new StringBuilder();
//	sb.append("[");
//	for(int i = 0; i < currentHand.size() ; i++) {
//		Card c = currentHand.get(i);
//		sb.append("{\"value\":"+c.getFancyValue()+",\"suit\":\""+c.getSuit()+"\"}");
//		if(currentHand.size()-1>i)
//		sb.append(",");
//	}
//	sb.append("]");
		
		Gson gson = new Gson();
		return gson.toJson(this.currentHand);
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