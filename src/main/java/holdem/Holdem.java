package holdem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.websocket.Session;

import com.google.gson.JsonObject;

import als.domain.Users;
import als.endpoint.Websocket;
import handelers.DBwriter;
import handelers.JSonParser;




/*
 * TODO
 * fixa så att blinds tas emot
 * fixa så att korten dealas från dealern
 * 
 */

public class Holdem implements Runnable{
	static boolean VERBOSE = false;

	public Map<String, Object> lastMessage = new HashMap<String, Object>();
	public Session lastSender;
	Scanner reader = new Scanner(System.in); 
	Hand[] players;
	double[] playerPayed;
	int[] playerScore;
	int dealer = 0;
	Hand lastBetter;
	Hand table;
	Deck deck;
	double bigBlinds= 30;
	ArrayList<Session> playersList;
	public Holdem() {}
	public void start(ArrayList<Session> playersList) {
		this.playersList = playersList;
		for(Session s : playersList) {
			try {
	    		JsonObject jsonObject = new JsonObject();
	    		jsonObject.addProperty("request", "message");
	    		jsonObject.addProperty("message", "Welcome to Texas Holdem");
	    		s.getBasicRemote().sendText(jsonObject.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		  Thread t1 = new Thread(this);
		    t1.start();
	}

	private int getActive() {
		int active = 0;
//		ArrayList<Hand> activeList = new ArrayList<Hand>();
		for(Hand h : players) {
			if(h != null) {
				active ++;
//				activeList.add(h);
			}
		}
		return active;
	}
		
//		
//		return false;
//	}
	public String getPrintableHand(Hand h) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < h.getHand().size() ; i++) {
			sb.append("[");
			Card c = h.getHand().get(i);
			sb.append(c.getShortValue()+c.getShortSuit());
			sb.append("]");
		}

		return sb.toString();
	}
	private void bettingRound() {
		sendMessage("-----------------------------------");
		
//		for(int i = 0; i < playersList.size() ; i++) {
		int i = 0;
		lastBetter = null;
//		while(players[i >= players.length ? 0 : i] != lastBetter && getActive() > 1) {

		while(players[i] != lastBetter ) {	
			
			if(players[i] == null)
			continue;
			
			
		if(playersList.get(i).isOpen() ){
    		JsonObject jsonObject = new JsonObject();
    		jsonObject.addProperty("request", "message");
    		jsonObject.addProperty("message", "Round begin");
    		sendMessage(jsonObject.toString(), i);
    		
//			sendMessage("Round begin ", i);

//    		 jsonObject = new JsonObject();
//    		jsonObject.addProperty("request", "message");
//    		jsonObject.addProperty("message", "Player turn");
//    		jsonObject.addProperty("playerName", Websocket.sessionToName.get(this.playersList.get(i)));
//    		sendMessage(jsonObject.toString());

    		jsonObject = new JsonObject();
    		jsonObject.addProperty("success", true);
    		jsonObject.addProperty("request", "update");
    		jsonObject.addProperty("game", "holdem");
    		jsonObject.addProperty("player", getPrintableHand(players[i]));
    		jsonObject.addProperty("table", getPrintableHand(table));
    		jsonObject.addProperty("bananas", DBwriter.getUser(Websocket.sessionToName.get(playersList.get(i))).getBananas());
    		jsonObject.addProperty("pot", Double.toString(getPot()));

    		sendMessage(jsonObject.toString(), i);
    		
    		jsonObject = new JsonObject();
    		jsonObject.addProperty("success", true);
    		jsonObject.addProperty("request", "update");
    		jsonObject.addProperty("game", "holdem");
    		jsonObject.addProperty("activePlayer", Websocket.sessionToName.get(this.playersList.get(i)));
    		sendMessage(jsonObject.toString());
    		
//			sendMessage(table.toString(), i);
//			sendMessage("the pot is "+Double.toString(getPot()));
			Map<String, Object> option = awaitPlayer(i);
//		players[i] = new Hand();
			

//			System.out.println(option);
//			System.out.println(option.get("raise"));
//			System.out.println(option.get("action"));
//			System.out.println(option.get("action").equals("raise"));
			 if(option.containsKey("action")
						&& option.get("action").equals("raise")) {
				 raise(i,  (String) option.get("raise"));
			}
			 else if(option.containsKey("action") 
				&& option.get("action").equals("call")) {
			call(i);
		}
		else if(option.containsKey("action") 
				&& option.get("action").equals("fold")) {
			fold(i);
					}
		
	
		
		
		
		
	}
			i++;
			if(i == players.length && lastBetter == null)
			break;
			if(i >= players.length)
				i = 0;
}
		
	}
	
	static void printIfVerbose(String s) {
		if(VERBOSE) {
			System.out.println(s);
		}
	}
	
	private boolean fold(int player) {
		players[player] = null;
		System.out.println("fold "+playerPayed[player]);
		return true;
	}
	private boolean call(int player) {
		playerPayed[player] += getOwed(player);
		System.out.println("call "+playerPayed[player]);
		return true;
	}
private boolean raise(int player, String raise) {
	Users user = DBwriter.getUser(Websocket.sessionToName.get(playersList.get(player)));
	double raiseAsDouble = Double.parseDouble(raise);
	if(user.getBananas() >= raiseAsDouble) {
	lastBetter = players[player];
	playerPayed[player] += Double.parseDouble(raise);
	System.out.println("raised "+playerPayed[player]);
	return true;
	}
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
	
	public boolean verifyPlayerInput(Map<String, Object> m, int player) {

		if(m != null && m.containsKey("action")) {
		String action = m.get("action").toString();
		
		if(getOwed(player) <= 0 && (action.equals("call")  || (action.equals("raise"))) ) {
			printIfVerbose("verifyPlayerInput is true");
			return true;
		}
		else if((action.equals("call")  || action.equals("fold") || action.equals("raise") ) ) {
			printIfVerbose("verifyPlayerInput is true");
			return true;
		}
		else 
			sendMessage("bad input", player);
		}
		printIfVerbose("verifyPlayerInput is false");
		return false;
	}
	
	
	
	
//	public boolean verifyRaise(String m, int player) {
//
//try {
//	Double.parseDouble(m);
//	return true;
//} catch (Exception e) {
//	sendMessage("bad input", player);
//	return false;
//}
//	}
	
	private void Showdown() {
		sendMessage("-----------------------------------------------");
		sendMessage("!!! SHOWDOWN !!!");
		sendMessage("-----------------------------------------------");
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
			findCombos(compareHand, hand);
		
			switch(playerScore[hand]) {
			case(100):
				sendMessage("Royal", hand);
			break;
			case(90):
				sendMessage("straight flush", hand);
			break;
			case(80):
				sendMessage("four of a kind", hand);
			break;
			case(70):
				sendMessage("fullhouse", hand);
			break;
			case(60):
				sendMessage("flush", hand);
			break;
			case(50):
				sendMessage("straight", hand);
			break;
			case(40):
				sendMessage("three of a kind", hand);
			break;
			case(30):
				sendMessage("two pair", hand);
			break;
			case(20):
				sendMessage("one pair", hand);
			break;
			default:
				sendMessage("high card", hand);
			break;
			}

		
	}
	}
	

	private void sendMessage(String string) {
for(Session s : playersList) {
	sendMessage(string, this.playersList.indexOf(s));
}
	}
	private void sendMessage(String string, int i) {
		try {
			playersList.get(i).getBasicRemote().sendText(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void findCombos(Hand h, int player) {
		playerScore[player] = 0;

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
	playerScore[player] = thisScore;
}
								
							}								
							}								
							}								
							}
			}
			
			
			
			
			
			
			
			
			
			
			
	
	}
	public Map<String, Object> awaitPlayer(int playerPos) {
		
		lastMessage.clear();
		lastSender = null;
		
		synchronized (this){

					while(!this.verifyPlayerInput(lastMessage, playerPos)) {
						printIfVerbose("stuck in while loop");
							try {
								this.playersList.get(playerPos).getBasicRemote().sendText("(1):Called (2):Fold (3):Raise ");

								printIfVerbose("sleeping in loop");
								this.wait();
							} 
							
							catch (IOException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					
					}
			  }
		
		

		printIfVerbose("loop finished");
		return lastMessage;
	}
//	public int awaitPlayerRaise(int playerPos) {
//		lastMessage = null;
//		lastSender = null;
//		
//		synchronized (this){
//
//					while(!this.verifyRaise(lastMessage, playerPos)) {
//
//							try {
//								this.playersList.get(playerPos).getBasicRemote().sendText("Raise how much?");
//								this.wait();
//							} 
//							
//							catch (IOException e) {
//								e.printStackTrace();
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//					
//					}
//			  }
//		
//		
//
//		return Integer.parseInt(lastMessage);
//	}
	
	
	

	public static void updateLastMessage(String message, Session s, Holdem h) {

		printIfVerbose("updating last message");
		Map<String, Object> retMap;
   	 retMap = JSonParser.parseJson(message);
		h.lastMessage = retMap;
		h.lastSender = s;
		
		     synchronized(h) {
		    	 printIfVerbose("waking from loop");
		          h.notify();
		     }	

		
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
				handRating = 90;
			}
			else if(fourOfAKind) {
				handRating = 80;
			}
			else if(fullHouse) {
				handRating = 70;
			}
			else if( h.getHand().get(0).getSuit() == h.getHand().get(1).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(2).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(3).getSuit()
					&& h.getHand().get(0).getSuit() == h.getHand().get(4).getSuit()) {
				handRating = 60;
			}
			else if(h.getHand().get(0).getValue() == lowestVal+4
					&& h.getHand().get(1).getValue() == lowestVal+3
					&& h.getHand().get(2).getValue() == lowestVal+2
					&& h.getHand().get(3).getValue() == lowestVal+1
					&& h.getHand().get(4).getValue() == lowestVal) {
				handRating = 50;
			}
			else if(threeOfAKind) {
				handRating = 40;
			}
			else if(twoPair) {
				handRating = 30;
			}
			else if(onePair) {
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

		@Override
		public void run() {

			players = new Hand[playersList.size()];
			playerPayed = new double[playersList.size()];
			playerScore = new int[playersList.size()];
			deck = new Deck();
			
			
//			for(int i = 0; i < playersList.size(); i++) {
//				
//				if(playersList.get(i).isOpen() ){
//						sendMessage("Round begin ", i);
//					int option = awaitPlayer(i);
//					players[i] = new Hand();
//					StubMethod("remove blinds from player, add blinds to pot (fold, raise or call)");
//					if(StubMethod("if Bigfolds or raise")) {
//						StubMethod("set owed (or payed)");
//					}
//				}
//			}
			
			{
				
				for (int i = 0; i < players.length; i++) {
					players[i] = new Hand();
					
				}
				
			int i = 0;
			lastBetter = null;
			while(players[i] != lastBetter ) {
			
				
				
			if(playersList.get(i).isOpen() ){
	    		JsonObject jsonObject = new JsonObject();
	    		jsonObject.addProperty("success", true);
	    		jsonObject.addProperty("request", "update");
	    		jsonObject.addProperty("game", "holdem");
	    		jsonObject.addProperty("activePlayer", Websocket.sessionToName.get(this.playersList.get(i)));
	    		sendMessage(jsonObject.toString());
	    		
				Map<String, Object> option = awaitPlayer(i);
				printIfVerbose("after loop");
				
				if(option.containsKey("action") && option.get("action").equals("call")) {
					players[i] = new Hand();
					call(i);
				}
				else if(option.containsKey("action") && option.get("action").equals("fold")) {
					fold(i);
							}
				
				else if(option.containsKey("action")
						&& option.get("action").equals("raise")
						&& option.containsKey("raise")) {
					players[i] = new Hand();
					raise(i, (String) option.get("raise"));
				}
			
			
			
			
		}
				i++;
				if(i == players.length && lastBetter == null)
					break;
					if(i >= players.length)
						i = 0;
	}
			}
			
			lastBetter = null;


			deck.shuffle();
			
			//Player cards dealt
			for (int card = 0; card < 2; card++) {
				for (int i = 0; i < playersList.size(); i++) {
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
				
				int highestScore = 0;
				int winner = 0;
				for (int i = 0; i < playerScore.length; i++) {
					if(playerScore[i] > highestScore) {
						highestScore = playerScore[i];
						winner = i;
					}
				}
				DBwriter.addBananas(Websocket.sessionToName.get(playersList.get(winner)), ((Double) getPot()).intValue() );
				
				for (int i = 0; i < playerScore.length; i++) {
					if(i != winner)
						DBwriter.removeBananas(Websocket.sessionToName.get(playersList.get(i)), ((Double) playerPayed[i]).intValue() );
				}
				

	    		JsonObject jsonObject = new JsonObject();
	    		jsonObject.addProperty("success", true);
	    		jsonObject.addProperty("request", "update");
	    		jsonObject.addProperty("game", "holdem");
	    		jsonObject.addProperty("winner", Websocket.sessionToName.get(this.playersList.get(winner)));
	    		sendMessage(jsonObject.toString());
		
		}

}
