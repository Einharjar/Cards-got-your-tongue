import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import holdem.Holdem;

@ServerEndpoint("/websocket")
public class TestEndpoint {
	boolean started = false;
	static ArrayList<Session> sessions = new ArrayList<Session>();;
	static Map<Integer, Lobby> games = new HashMap<Integer, Lobby>();;
	static Map<Session, Lobby> sessionToGame = new HashMap<Session, Lobby>();;
	static Map<Session, String> sessionToName = new HashMap<Session, String>();;
    @OnMessage
    public void onMessage(String message, Session session) {
//    	if(!sessionToName.containsKey(session)) {
//            if(message.startsWith("{\"username\": ") && message.endsWith("\"}")) {
//            	onServerMessage("Welcome online!", session);
//            	String subs = message.substring(16, message.indexOf('"', 16));
//            	sessionToName.put(session, subs);
//            }
//            }
//    	
//    	else {
    	 if(message.startsWith("{\"game\": ") && message.endsWith("\"}")) {
            	String subs = message.substring(10, message.indexOf('"', 10));
            	sessionToGame.get(session).forwardMessage(subs, session);
            }

        if(message.equals("{\"string\": \"newlobby\"}")
        		//&& !userGame.containsKey(session)
        		) {
        	userRemoveLobby(session);
        	onServerMessage("You've just created a new lobby", session);
        	new Lobby(session, Holdem.class);
        }
        if(message.equals("{\"string\": \"thislobby\"}")) {
        	onServerMessage("\"{\"string\": \""+sessionToGame.get(session)+"\"}\"", session);
        }
        if(message.startsWith("{\"join\": ") && message.endsWith("\"}")) {
        	onServerMessage("Welcome to the Lobby", session);
        	onServerMessage(message.substring(10, message.indexOf('"', 10)),session);
        	userJoinLobby(session, games.get(Integer.parseInt(message.substring(10, message.indexOf('"', 10)))));
        }
        if(message.equals("[]")) {
        	
        	
        }
        if(message.equals("{\"string\": \"start\"}")
//        		&& (sessionToGame.get(session).game != null)
        		 && sessionToGame.get(session).host.equals(session)) {
        	onServerMessage("Game starting", session);
        	games.remove(sessionToGame.get(session).id);
        	sessionToGame.get(session).start();
        }
        if(message.equals("{\"string\": \"getlobbies\"}")) {
        	onServerMessage("{\"string\": \""+games.toString()+"\"}", session);
        	onServerMessage("{\"string\": \""+games.size()+"\"}", session);
        }
    	
    	}
//    }

    @OnClose
    public void onClose(Session session) {
    	sessions.remove(session);
        System.out.println("onClose");
    }

    @OnOpen
    public void onOpen(Session session) {
    	sessions.add(session);
        System.out.println("onOpen");
        System.out.println(session.getOpenSessions().toArray());
        System.out.println(session.getOpenSessions().size());
//        while(!started) {
//        	
//        }
//        new holdem.Game();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("onError");
    }

    public void onServerMessage(String message, int user) {
        try {
			sessions.get(user)
			        .getBasicRemote() // see also getAsyncRemote()
			        .sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static void onServerMessage(String message, Session session) {
        try {
			session
			        .getBasicRemote() // see also getAsyncRemote()
			        .sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static void newLobby(Lobby lobby) {
		
		games.put(lobby.id, lobby);
	}
    public static int firstFreeID() {
    	int i = 0;
    	while(games.containsKey(i)) {
    		i++;
    	}
    	return i;
    }
    
    
    public static void userJoinLobby(Session s, Lobby l) {
    	if(!sessionToGame.containsKey(s)) {
    		sessionToGame.put(s, l);
    		l.players.add(s);
    	}
    }
    
    public static void userRemoveLobby(Session s) {
    	if(sessionToGame.containsKey(s)) {
    		sessionToGame.get(s).players.remove(s);
    		sessionToGame.remove(s);
    	}
    }
    
    
    
    
    
    
    
//    public void onServerMessage(Session session, Message message) {
//        sessions.get(message.getToUserName())
//                .getBasicRemote() // see also getAsyncRemote()
//                .sendText(message.getContent());
//    }

}
