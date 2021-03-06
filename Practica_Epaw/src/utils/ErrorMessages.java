package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ErrorMessages {
	
	private String origin; // Variable que indicara la pagina origen donde se produce el error
	private HashMap<String, String> errors; // HashMap que contiene los mensajes de erorres.
	
	public ErrorMessages() {
		this.origin = "";
		this.errors = new HashMap<String, String>();
	}
	
	public ErrorMessages(String origin) {
		this.origin = origin;
		this.errors = new HashMap<String, String>();
	}
	
	public String getOrigin() { return this.origin; }
	
	public HashMap<String, String> getErrors() {
		return this.errors;
	}
	
	public String getErrorMessage(String errorKey) {
		return this.errors.get(errorKey);
	}
	
	public boolean addError(String errorKey, String errorMessage) {
		boolean add = false;
		if (GeneralUtils.existObjectInMap(this.errors, errorKey) == true) {
			String oldMessage = this.errors.get(errorKey);
			add = this.errors.replace(errorKey, oldMessage, errorMessage);
		} else {
			
			try {
				this.errors.put(errorKey, errorMessage);
				add = true;
			} catch (Exception e) {
				add = false;
			}
			
		}
		return add;
	}
	
	public boolean addError(ErrorMessages other) {
		boolean add = true;
		try {
			Set<String> keys = (Set<String>) other.getErrors().keySet();
			for (String key : keys) {
				GeneralUtils.addObjectToMap(this.errors, key, other.getErrorMessage(key));
			}
		} catch(Exception e) {
			e.printStackTrace();
			add = false;
		}
		return add;
	}
	
	public boolean removeError(String errorKey) {
		return GeneralUtils.removeObjectToMap(this.errors, errorKey);
	}
	
	public boolean haveErrors() {
		return (this.errors.size() > 0);
	}
	
	public String getJSON() {
		String json = "{\"origin\":\"" + this.origin + "\", \"errors\":[";
		for(String key : this.errors.keySet()) {
			json += "{\"name\":\"" + key + "\", ";
			json += "\"error\":\"" + this.errors.get(key) + "\"}";
			json += ", ";
		}
		if (this.errors.size() > 0) {
			json = json.substring(0, json.length() - ", ".length());
		}
		json += "]}";
		return json;
	}

}
