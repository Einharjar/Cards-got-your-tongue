package handelers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class JSonParser {

//List<AccountDetails> requests = new ArrayList<AccountDetails>();
	









public static void main(String[] args) throws ParseException, JsonSyntaxException, IOException {
//	parseJson("{\r\n" + 
//			"\"request\" : \"register\",\r\n" + 
//			"\"username\" : \"John\",\r\n" + 
//			"\"password\": \"qwerty\",\r\n" + 
//			"\"bananas\" : 0.0,\r\n" + 
//			"\"userId\" : 1688.0,\r\n" + 
//			"\"details\" :  [ {\r\n" + 
//			"\"personId\" : 1688.0,\r\n" + 
//			"\"firstName\" : \"John\",\r\n" + 
//			"\"lastName\" : \"Smith\"\r\n" + 
//			"} ]\r\n" + 
//			"}");
//	parseJson("{\"name\":\"Jon\",\"age\":33,\"isFemale\":false,\"contact\":{\"email\":\"jon@example.com\",\"phone\":\"+46123456789\"},\"publications\":[\"a665ca20-4161-42cb-937a-a2bdd4aa25de\",\"270813ad-5075-4e1f-8a3f-29a934714892\"],\"publicKey\":null}\r\n");


    Type type = new TypeToken<HashMap<String, Object>>() {
    }.getType();
    Gson gson = new Gson();

//  String str = gson.toJson(object);
	Map<String, Object> map = gson.fromJson("{\"name\":\"Jon\",\"age\":33,\"isFemale\":false,\"contact\":{\"email\":\"jon@example.com\",\"phone\":\"+46123456789\"},\"publications\":[\"a665ca20-4161-42cb-937a-a2bdd4aa25de\",\"270813ad-5075-4e1f-8a3f-29a934714892\"],\"publicKey\":null}\r\n", type);

}


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
public static Map<String, Object> parseJson(String message) {
	
//	Map<String, Object> retMap = new HashMap<String, Object>();
	
	
    Type type = new TypeToken<HashMap<String, Object>>() {
    }.getType();
    Gson gson = new Gson();

    
//    String str = gson.fromJson(message, type);
//  	Map<String, Object> map = gson.fromJson(str, type);
//      
//    for (String key : map.keySet()) {
//        Object obj = map.get(key);
//        
//        if (obj instanceof List) {
//            System.out.print(key+ " : ");
//            System.out.println(" [ ");
//            for (Object o : (List) obj) {
//                if (o instanceof Map) {
//                    loop((Map) o);
//                } else {
//                    System.out.println(key + " : " + o);
//                }
//            }
//            System.out.println(" ] ");
//        } else if (obj instanceof Map) {
//            System.out.print(key+ " : ");
//            System.out.println(" [ ");
//            loop((Map) obj);
//            System.out.println(" ] ");
//        } else {
//            System.out.println(key + " : " + obj);
//        }
//    }
    
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




	public JSonParser(String message) {
		
	   // Create Json and serialize
	   JsonObject json = Json.createObjectBuilder()
	     .add("name", "Falco")
	     .add("age", BigDecimal.valueOf(3))
	     .add("biteable", Boolean.FALSE).build();
	   String result = json.toString();

       StringWriter swriter = new StringWriter();
	   
	   JsonGenerator jsonGenerator =
			   Json.createGenerator(swriter);
			   jsonGenerator.writeStartObject()
			   .write("name", "Jon")
			   .write("age", 33)
			   .write("isFemale", false)
			   .writeStartObject("contact")
			   .write("email", "jon@example.com")
			   .write("phone", "+46123456789")
			   .writeEnd()
			   .writeStartArray("publications")
			   .write("a665ca20-4161-42cb-937a-a2bdd4aa25de")
			   .write("270813ad-5075-4e1f-8a3f-29a934714892")
			   .writeEnd()
			   .writeNull("publicKey")
			   .writeEnd();
			   jsonGenerator.flush();
			   jsonGenerator.close();
	   
	   System.out.println(swriter);
	}

	public JsonObject  stringToJson(String s){
		
		
		return null;
		
	}
	public static JsonObject  jsonToObject(String s){
	       StringWriter swriter = new StringWriter();
		   
		   
		   JsonGenerator jsonGenerator =
				   Json.createGenerator(swriter);
				   jsonGenerator.writeStartObject()
				   .write("name", "Jon")
				   .write("age", 33)
				   .write("isFemale", false)
				   .writeStartObject("contact")
				   .write("email", "jon@example.com")
				   .write("phone", "+46123456789")
				   .writeEnd()
				   .writeStartArray("publications")
				   .write("a665ca20-4161-42cb-937a-a2bdd4aa25de")
				   .write("270813ad-5075-4e1f-8a3f-29a934714892")
				   .writeEnd()
				   .writeNull("publicKey")
				   .writeEnd();
				   jsonGenerator.flush();
				   jsonGenerator.close();
				   
//				   System.out.println(swriter);
				   
		   JsonObject json = Json.createObjectBuilder()
		     .add("name", "Falco")
		     .add("age", BigDecimal.valueOf(3))
		     .add("biteable", Boolean.FALSE).build();
		   String result = swriter.toString();
//		String result = "";

//		   System.out.println(json);
	    final JsonParser parser = Json.createParser(new StringReader(result));
	    String key = null;
	    String value = null;
	    while (parser.hasNext()) {
	         final Event event = parser.next();
	         switch (event) {
//	         default:
//	        	 System.out.println(key + ", " + value);
	            case KEY_NAME:
	                 key = parser.getString();
	                 System.out.print("key: "+key+", Value: ");
	                 break;
	            case VALUE_STRING:
	                 String string = parser.getString();
	                 System.out.println(string);
	                 break;
	            case VALUE_NUMBER:
	                BigDecimal number = parser.getBigDecimal();
	                System.out.println(number);
	                break;
	            case VALUE_TRUE:
	                System.out.println(true);
	                break;
	            case VALUE_FALSE:
	                System.out.println(false);
	                break;
	            }
	        }
	        parser.close();
		
		return null;
		
	}
}
