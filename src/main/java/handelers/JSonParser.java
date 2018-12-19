package handelers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import als.domain.Users;


public class JSonParser {



public static void printJson(Object object) {

    Type type = new TypeToken<HashMap<String, Object>>() {
    }.getType();
    Gson gson = new Gson();

  String str = gson.toJson(object);
	Map<String, Object> map = gson.fromJson(str, type);
    
  for (String key : map.keySet()) {
      Object obj = map.get(key);
      
      if (obj instanceof List) {
          System.out.print(key+ " : ");
          System.out.println(" [ ");
          for (Object o : (List) obj) {
              if (o instanceof Map) {
                  loop((Map) o);
              } else {
                  System.out.println(key + " : " + o);
              }
          }
          System.out.println(" ] ");
      } else if (obj instanceof Map) {
          System.out.print(key+ " : ");
          System.out.println(" [ ");
          loop((Map) obj);
          System.out.println(" ] ");
      } else {
          System.out.println(key + " : " + obj);
      }
  }
}
public static String toJson(Object o) {
    Gson gson = new Gson();
    String j = gson.toJson(o);
	return j;
}
public static Users userFromJson(Object lmt) {

	String map = toJson(lmt);
	
    Type type = new TypeToken<Users>() {}.getType();
    Gson gson = new Gson();
    Users u = gson.fromJson(map, type);
	return u;
}
public static Map<String, Object> parseJson(String message) {
	
//	Map<String, Object> retMap = new HashMap<String, Object>();
	
	
    Type type = new TypeToken<HashMap<String, Object>>() {
    }.getType();
    Gson gson = new Gson();

    
    
    return gson.fromJson(message, type);
}

private static void loop(Map<String, Object> map) {
    for (String key : map.keySet()) {
        Object obj = map.get(key);
        if (obj instanceof List) {
            for (Object o : (List) obj) {
                if (o instanceof Map) {
                    loop((Map) o);
                } else {
                    System.out.println(key + " : " + o);
                }
            }
        } else if (obj instanceof Map) {
            loop((Map) obj);
        } else {
            System.out.println(key + " : " + obj);
        }
    }
}





}
