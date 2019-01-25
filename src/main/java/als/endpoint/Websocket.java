package als.endpoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import als.domain.PersonDetails;
import als.domain.Users;
import handelers.DBwriter;
import handelers.JSonParser;
import holdem.Holdem;

@ServerEndpoint("/websocket")
public class Websocket {
	boolean started = false;
	static ArrayList<Session> sessions = new ArrayList<Session>();;
	static Map<Integer, Lobby> games = new HashMap<Integer, Lobby>();;
	static Map<Session, Lobby> sessionToGame = new HashMap<Session, Lobby>();;
	public static Map<Session, String> sessionToName = new HashMap<Session, String>();;
    @OnMessage
    public void onMessage(String message, Session session) {
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	 retMap = JSonParser.parseJson(message);
    	
    	System.out.println(retMap.toString());
    	
    	 if(!retMap.containsKey("request"))
    		 return;
    	 
    	 String requestType = (String) retMap.get("request");
     	System.out.println(requestType);
    	 
    	if(!sessionToName.containsKey(session)) {
            if(requestType.equals("login")) {
            	String username = (String) retMap.get("username");
            	String password = (String) retMap.get("password");
            	if(DBwriter.autenticateUser(username, password)) {
            		JsonObject jsonObject = new JsonObject();
            		jsonObject.addProperty("request", "login");
            		jsonObject.addProperty("success", false);
                	sessionToName.put(session, username);
                	onServerMessage(jsonObject.getAsString(), session);
            	}
            	else {
            		JsonObject jsonObject = new JsonObject();
            		jsonObject.addProperty("request", "login");
            		jsonObject.addProperty("success", false);
                	onServerMessage(jsonObject.getAsString(), session);
            	}
            		
            }
            else if(requestType.equals("register")) {
             	 System.out.println(retMap.get("user").getClass().getSimpleName());
            	try {
            		Users u = JSonParser.userFromJson(retMap.get("user"));
            		PersonDetails p = u.getDetails();
            		
            		
            		DBwriter.writeNewPerson(p);
            		DBwriter.writeNewUser(u, u.getUserId());
                	sessionToName.put(session, (String) retMap.get("username"));

                	onServerMessage("Welcome online!", session);
                	
            	} catch (NullPointerException e) {
	            	onServerMessage("Missing field", session);
	            	e.printStackTrace();
				}catch (Exception e) {
	            	onServerMessage("unknown error", session);
	            	e.printStackTrace();
				}
            }
            
            
            }
    	
    	else {
    	
    	 if(requestType.equals("join")) {
    		 
    		int uid = ((Double) retMap.get("join")).intValue();
    		
    		
      		 if(retMap.get("join") instanceof String) {
              	userJoinLobby(session, games.get(retMap.get("join")));
             	onServerMessage("Welcome to the Lobby", session);
      		 }
      		 else if(retMap.get("join") instanceof BigDecimal) {
              	userJoinLobby(session, games.get( ((BigDecimal)retMap.get("join")).intValue()  ));
             	onServerMessage("Welcome to the Lobby", session);
             	}
    		 else if(retMap.get("join") instanceof Double) {
               	userJoinLobby(session, games.get( uid  ));
             	onServerMessage("Welcome to the Lobby", session);
             	}
      		 
         }
    	 
    	 if(requestType.equals("game")) {
//            	String subs = message.substring(10, message.indexOf('"', 10));
    		 if(retMap.get("game") instanceof String)
            	sessionToGame.get(session).forwardMessage((String) retMap.get("game"), session);

    		 else if (retMap.get("game") instanceof BigDecimal)
            	sessionToGame.get(session).forwardMessage( Integer.toString(((BigDecimal)retMap.get("game")).intValue()), session);
    		 else if(retMap.get("game") instanceof Double)
             	sessionToGame.get(session).forwardMessage( Integer.toString(((Double)retMap.get("game")).intValue()), session);

    	 
    	 }

        	        if(requestType.equals("string")) {

            	        if(retMap.get("string").equals("newlobby")) {
            	        	userRemoveLobby(session);
            	        	onServerMessage("You've just created a new lobby", session);
            	        	new Lobby(session, Holdem.class);
        }
        	        else if(retMap.get("string").equals("thislobby")) {
        	        	onServerMessage("\"{\"string\": \""+sessionToGame.get(session)+"\"}\"", session);
        }
        	        else if(retMap.get("string").equals("start")
        	        		&& sessionToGame.get(session).host.equals(session)) {
        	        	onServerMessage("Game starting", session);
        				games.remove(sessionToGame.get(session).id);
        				sessionToGame.get(session).start();
        }
        	        else if(retMap.get("string").equals("getlobbies")) {
        	onServerMessage("{\"string\": \""+games.toString()+"\"}", session);
        	onServerMessage("{\"string\": \""+games.size()+"\"}", session);
        }

        	        }
    	}
    }
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
        throwable.printStackTrace();
    	onServerMessage("error: "+ throwable, session);
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
    	if(!sessionToGame.containsKey(s) && l != null && s.isOpen()) {
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
