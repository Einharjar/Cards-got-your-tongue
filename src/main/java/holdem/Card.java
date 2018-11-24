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
		return this.value + " of " + this.suit;
	}

	
	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.getValue(), o.getValue());
	}
}
