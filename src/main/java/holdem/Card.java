package holdem;

import com.google.gson.Gson;

public class Card implements Comparable<Card>{
	private int value;
	private Suits suit;

	public Card(int value, Suits suit) {
		this.value = value;
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public Suits getSuit() {
		return suit;
	}
	
	public String getFancyValue() {
		switch(value) {
		case(1):
			return "Ace";
			case(11):
				return "Jack";
			case(12):
				return "Queen";
			case(13):
				return "King";
			default:
				return Integer.toString(value);
}
		
	}
	public String getShortValue() {
		switch(value) {
		case(1):
			return "A";
			case(11):
				return "J";
			case(12):
				return "Q";
			case(13):
				return "K";
			default:
				return Integer.toString(value);
}
		
	}
	public String getShortSuit() {
		return suit.name().substring(0,1);
	}
	
	
	
	@Override
	public String toString() {
		Gson gson = new Gson();
//		String retStr = "";
		return gson.toJson(this);
//		if(this.getValue() == 13)
//			return "King of " + this.suit;
//		else if(this.getValue() == 12)
//			return "Queen of " + this.suit;
//		else if(this.getValue() == 11)
//			return "Jack of " + this.suit;
//		else if(this.getValue() == 1)
//			return "Ace of " + this.suit;
//		else
//		return this.value + " of " + this.suit;
	}

	
	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.getValue(), o.getValue());
	}
}
