import java.util.ArrayList;

import javax.websocket.Session;

import holdem.Holdem;

public class Lobby {
	ArrayList<Session> players = new ArrayList<>();
	int maxPlayers = 10;
	Class gameType;
	int id;
	Session host;
	Holdem game;
	public Lobby(Session host, Class gameType) {
		id = TestEndpoint.firstFreeID();
		this.host = host;
		TestEndpoint.userJoinLobby(host, this);
		this.gameType = gameType;
		TestEndpoint.newLobby(this);
		game = new Holdem();

	}
//	@Override
//	public String toString() {
//		return "Lobby [players=" + players.size() + "/" + maxPlayers + ", gameType=" + gameType + "]";
//	}
//	
	public void forwardMessage(String m, Session s) {
		game.updateLastMessage(m, s, game);
	}
	public void start() {
		game.start(players);
	}
	@Override
	public String toString() {
		return "Lobby [players=" + players.size() + "/" + maxPlayers + ", gameType=" + gameType.getSimpleName() + ", id=" + id
				+ "]\n";
	}
}
