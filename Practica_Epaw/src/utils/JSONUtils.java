/**
 * Classe Statica con utilidades para JSON.
 * @author Marc Pérez - 173287
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {

	public static <T> T returnJSONObject(String json, Class<T> type, String dateFormat) {
		T jsonObject = null;
		try {
			Gson gson = null;
			if (dateFormat != null) {
				gson = new GsonBuilder().setDateFormat(dateFormat).create();
			} else {
				gson = new Gson();
			}
			jsonObject = gson.fromJson(json, (Class<T>) type);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject = null;
		}
		return jsonObject;
	}
	
	public static <T> T returnJSONObject(String json, Class<T> type) {
		return returnJSONObject(json, type, null);
	}
	
	public static <T> String getJSON(T obj, String dateFormat) {
		String json = "";
		try {
			Gson gson = null;
			if (dateFormat != null) {
				gson = new GsonBuilder().setDateFormat(dateFormat).create();
			} else {
				gson = new Gson();
			}
			json = gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
			json = "";
		}
		return json;
	}
	
	public static <T> String getJSON(T obj) {
		return getJSON(obj, null);
	}
	
}
