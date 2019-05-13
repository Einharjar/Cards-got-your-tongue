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
    	
    	System.out.println("Message recieved: "+message);
    	
    	 if(!retMap.containsKey("request"))
    		 return;
    	 
    	 String requestType = (String) retMap.get("request");
//     	System.out.println(requestType);
    	 
    	if(!sessionToName.containsKey(session)) {
            if(requestType.equals("login")) {
            	String username = (String) retMap.get("username");
            	String password = (String) retMap.get("password");
            	if(DBwriter.autenticateUser(username, password)) {
            		JsonObject jsonObject = new JsonObject();
            		jsonObject.addProperty("request", "login");
            		jsonObject.addProperty("success", true);
                	sessionToName.put(session, username);
                	onServerMessage(jsonObject.toString(), session);
            	}
            	else {
            		JsonObject jsonObject = new JsonObject();
            		jsonObject.addProperty("request", "login");
            		jsonObject.addProperty("success", false);
                	onServerMessage(jsonObject.toString(), session);
            	}
            		
            }
            else if(requestType.equals("register")) {
             	 System.out.println(retMap.get("user").getClass().getSimpleName());
            	try {
            		Users u = JSonParser.userFromJson(retMap.get("user"));
            		PersonDetails p = u.getDetails();
            		
            		
            		DBwriter.writeNewPerson(p);
            		DBwriter.writeNewUser(u.getUserName(), u.getPassword(), u.getUserId(), p.getFirstName(), p.getLastName());
                	sessionToName.put(session, (String) retMap.get("username"));

                	
            	} catch (NullPointerException e) {
	            	e.printStackTrace();
				}catch (Exception e) {
	            	e.printStackTrace();
				}
            }
            
            
            }
    	
    	else {
    	
    	 if(requestType.equals("join")) {
    		 

    		 String lobbyNrAsString = (String) retMap.get("join");
    		 double lobbyNrAsDouble = Double.parseDouble(lobbyNrAsString);
    		int uid =  ((int) lobbyNrAsDouble);

    		
               	userJoinLobby(session, games.get( uid  ));
      		 
         }
    	 
    	 if(requestType.equals("game")) {
//            	String subs = message.substring(10, message.indexOf('"', 10));
//    		 if(retMap.get("game") instanceof String)
            	sessionToGame.get(session).forwardMessage( message, session);

//    		 else if (retMap.get("game") instanceof BigDecimal)
//            	sessionToGame.get(session).forwardMessage( Integer.toString(((BigDecimal)retMap.get("game")).intValue()), session);
//    		 else if(retMap.get("game") instanceof Double)
//             	sessionToGame.get(session).forwardMessage( Integer.toString(((Double)retMap.get("game")).intValue()), session);

    	 
    	 }

        	        if(requestType.equals("string")) {

            	        if(retMap.get("string").equals("newlobby")) {
            	        	userRemoveLobby(session);
            	        	Lobby newLobby = new Lobby(session, Holdem.class);

            	    		JsonObject jsonObject = new JsonObject();
            	    		jsonObject.addProperty("request", "newlobby");
            	    		jsonObject.addProperty("success", true);
            	    		jsonObject.addProperty("id", newLobby.id);
            	    		jsonObject.addProperty("name", newLobby.toString());
            	    		jsonObject.addProperty("host", sessionToName.get(session));
            	    		jsonObject.addProperty("players", newLobby.players.size());
            	    		jsonObject.addProperty("playersmax", newLobby.maxPlayers);
            	         	onServerMessage(jsonObject.toString(), session);
//            	        	onServerMessage("You've just created a new lobby", session);
        }
        	        else if(retMap.get("string").equals("thislobby")) {
        	        	onServerMessage("\"{\"string\": \""+sessionToGame.get(session)+"\"}\"", session);
        }
        	        else if(retMap.get("string").equals("start")
        	        		&& sessionToGame.get(session).host.equals(session)) {
        	        	System.out.println("Game starting");
        	    		JsonObject jsonObject = new JsonObject();
        	    		jsonObject.addProperty("request", "start");
        	    		jsonObject.addProperty("success", true);
        	    		for(Session s : sessionToGame.get(session).players) {
            	        	onServerMessage(jsonObject.toString(), s);
        	    		}
        				games.remove(sessionToGame.get(session).id);
        				sessionToGame.get(session).start();
        }
        	        else if(retMap.get("string").equals("getlobbies")) {

                		JsonObject jsonHeader = new JsonObject();
                		jsonHeader.addProperty("request", "clearlobbies");
                		jsonHeader.addProperty("success", true);
                    	onServerMessage(jsonHeader.toString(), session);
                    	
                    		for(Lobby l : games.values()) {
                        		JsonObject jsonObject = new JsonObject();
                        		jsonObject.addProperty("request", "newlobby");
                	    		jsonObject.addProperty("success", true);
                        		jsonObject.addProperty("id", Integer.toString(l.id));
                        		jsonObject.addProperty("host", this.sessionToName.get(l.host));
                        		jsonObject.addProperty("playerscurr", l.players.size());
                        		jsonObject.addProperty("playersmax", l.maxPlayers);
                        		jsonObject.addProperty("name", "new texas holdem lobby");
                            	onServerMessage(jsonObject.toString(), session);
                    		}
        }

        	        }
    	}
    }
    public void onClose(Session session) {
    	sessions.remove(session);
    	if(sessionToGame.containsKey(session)) {
    		JsonObject jsonHeader = new JsonObject();
    		jsonHeader.addProperty("request", "lobby");
    		jsonHeader.addProperty("playerLeft", sessionToName.get(session));
        	for(Session s: sessionToGame.get(session).players)
        		onServerMessage(jsonHeader.toString(), s);
    	}
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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!onError!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        throwable.printStackTrace();
    	onServerMessage("error: "+ throwable, session);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        userRemoveLobby(session);
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
    	if(l.players.size()>=l.maxPlayers)
    		return;
    	if(!sessionToGame.containsKey(s) && l != null && s.isOpen()) {
    		sessionToGame.put(s, l);
    		l.players.add(s);

    		JsonObject jsonObject = new JsonObject();
    		jsonObject.addProperty("request", "join");
    		jsonObject.addProperty("success", true);
    		jsonObject.addProperty("lobby", l.id);
    		jsonObject.addProperty("id", Integer.toString(l.id));
    		jsonObject.addProperty("host", sessionToName.get(l.host));
    		jsonObject.addProperty("playerscurr", l.players.size());
    		jsonObject.addProperty("playersmax", l.maxPlayers);
    		jsonObject.addProperty("name", "new texas holdem lobby");
         	onServerMessage(jsonObject.toString(), s);
    	}
    }
    
    public static void userRemoveLobby(Session s) {
    	if(sessionToGame.containsKey(s)) {
    		Lobby playersLobby = sessionToGame.get(s);
    		playersLobby.players.remove(s);
    		sessionToGame.remove(s);
    		if(playersLobby.host == s){
    			games.remove(playersLobby.id);
    		}
    	}
    }
    
    
    
    
    
    
    
//    public void onServerMessage(Session session, Message message) {
//        sessions.get(message.getToUserName())
//                .getBasicRemote() // see also getAsyncRemote()
//                .sendText(message.getContent());
//    }

}
