package als.endpoint;
import java.util.ArrayList;

import javax.websocket.Session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import holdem.Holdem;

public class Lobby {
	ArrayList<Session> players = new ArrayList<>();

	int maxPlayers = 10;
	Class gameType;
	int id;
	Session host;
	Holdem game;
	public Lobby(Session host, Class gameType) {
		id = Websocket.firstFreeID();
		this.host = host;
		Websocket.userJoinLobby(host, this);
		this.gameType = gameType;
		Websocket.newLobby(this);
		game = new Holdem();

	}
//	@Override
//	public String toString() {
//		return "Lobby [players=" + players.size() + "/" + maxPlayers + ", gameType=" + gameType + "]";
//	}
//	
	public void forwardMessage(String m, Session s) {
		Holdem.updateLastMessage(m, s, game);
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
