import java.util.ArrayList;

import javax.websocket.WebSocketContainer;


public class Game {
	Hand[] players;
	double[] playerPayed;
	int dealer = 0;
	int pot = 0;
Hand table;
Deck deck;
WebSocketContainer[] ws;

	public Game(int playersInt) {
		for (int i = 0; i < playerPayed.length; i++) {
			
		}
		deck = new Deck();
		
		
		
		
		
		
		for(int i = 0; i < playersInt; i++) {
			if(StubMethod("Websocket[i] Open and raise or call")){
				players[i] = new Hand();
				StubMethod("remove blinds from player, add blinds to pot (fold, raise or call)");
				if(StubMethod("if Bigfolds or raise")) {
					StubMethod("set owed (or payed)");
				}
			}
		}
		boolean raisedThisRound = true;
		while(raisedThisRound ) {
			raisedThisRound = false;
			
			
			for (int i = 0; i < playersInt; i++) {
				if(players[i] != null) {
					
					if(StubMethod("called")) {
						StubMethod("Payed ++");
					}
					else if(StubMethod("Folded")) {
						players[i] = null;
					}
					
					else if(StubMethod("Raised")) {
						raisedThisRound = true;
					}
				}
				
			}
		}
		
		
		deck.shuffle();
		
		//Player cards dealt
		for (int card = 0; card < 2; card++) {
			for (int i = 0; i < playersInt; i++) {
				if(players[i] != null) {
					players[i].AddCard(deck);
					}
			}
		}

		//Play of hands
		
		
		
		
		//The Flop
			table = new Hand();
			for (int j = 0; j < 3; j++) {
				table.AddCard(deck);
			}
				
			
			// The turn
			table.AddCard(deck);	
			

			StubMethod("play of hands");
			
			
			// The river
			table.AddCard(deck);
			
			StubMethod("play of hands");
			
			StubMethod("Showdown");
			
			System.out.println(table);
			for (int i = 0; i < playersInt; i++) {
				System.out.println(players[i]);
			}
	}

	public static void main(String[] args) {
		new Game(0);
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

	private int getPlayerResponse(int player){
		return 1;
	}


}
