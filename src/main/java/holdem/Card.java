package holdem;


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

	@Override
	public String toString() {
		if(this.getValue() == 13)
			return "King of " + this.suit;
		else if(this.getValue() == 12)
			return "Queen of " + this.suit;
		else if(this.getValue() == 11)
			return "Jack of " + this.suit;
		else if(this.getValue() == 1)
			return "Ace of " + this.suit;
		else
		return this.value + " of " + this.suit;
	}

	
	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.getValue(), o.getValue());
	}
}
