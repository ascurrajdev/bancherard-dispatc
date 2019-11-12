package py.com.sodep.bancard.common;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DispatcherUtil {

	public static String removeSpacesOnTheLeft(String word) {
		String temp = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != ' ') {
				temp = word.substring(i);
				break;
			}
		}
		return temp;
	}

	public static String removeSpacesOnTheRight(String word) {
		String temp = "";
		for (int i = word.length() - 1; i >= 0; i--) {
			if (word.charAt(i) != ' ') {
				temp = word.substring(0, i);
				break;
			}
		}
		return temp;
	}

	public static String substr(String string, int initial, int end){
		int max = end - initial;
		String buffer = new String();
		for(int i = 0; i < max; i++){
			buffer = buffer + string.charAt(initial++);
		}
		return buffer.trim();
	}

	public static String substr(String string, int initial){
		int end = string.length();
		return substr(string, initial, end);
	}

	public static String zerosToLeft(int number, int totalLength){
		if (String.valueOf(number).length() > totalLength)
			return String.valueOf(number);
		String format = "%0"+ totalLength +"d";
		return String.format(format, number);
	}

	public boolean isJSONValid(final String test) {
	    try {
	        new JSONObject(test);
	    } catch (JSONException ex) {
	        // Si es un JSONArray se valida de esta forma
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
}
