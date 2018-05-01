/**
 * Clase Statica que contiene utilidades para validar campos o parametros recividos por parametros.
 * @author Marc Pérez - 173287
 *
 */

package utils;

import java.util.List;
import java.util.Map;

public class ValidationUtils {

	public static <T, S> boolean equals(T a, S b) {
		boolean result = false;
		try {
			result = a.equals(b);
		} catch(Exception e) {
			result = false;
		}
		return result;
	}
	
	public static <T> boolean isNaN(T obj) {
		boolean result = true;
		try {
			if (obj instanceof Number) {
				result = false;
			}
		} catch(Exception e) {
			result = true;
		}
		return result;
	}
	
	public static <T> boolean isNull(T obj) {
		boolean result = true;
		try {
			result = (obj == null) ? true : false;
		} catch(Exception e) {
			result = true;
		}
		return result;
	}
	
	public static <T> boolean isEmpty(T obj) {
		boolean result = true;
		try {
			if (isNull(obj) == false) {
				if (obj instanceof String) {
					result = equals(obj, "");
				}
			}
		} catch(Exception e) {
			result = true;
		}
		return result;
	}
	
	public static <T> boolean isNotEmpty(T obj) {
		return !isEmpty(obj);
	}
	
	public static <T> boolean haveMinimumLength(T obj, int length) {
		boolean haveMinimum = false;
		
		try {
			if (obj instanceof String) {
				if (((String) obj).length() < length) { haveMinimum = true; }
			} else if (obj instanceof List) {
				if (((List) obj).size() < length) { haveMinimum = true; }
			} else if (obj instanceof Map) {
				if (((Map) obj).size() < length) { haveMinimum = true; }
			}
		} catch (Exception e) {
			haveMinimum = false;
			e.printStackTrace();
		}
		
		return haveMinimum;
	}
	
	public static <T> boolean haveMaxLength(T obj, int length) {
		boolean haveMinimum = false;
		
		try {
			if (obj instanceof String) {
				if (((String) obj).length() > length) { haveMinimum = true; }
			} else if (obj instanceof List) {
				if (((List) obj).size() > length) { haveMinimum = true; }
			} else if (obj instanceof Map) {
				if (((Map) obj).size() > length) { haveMinimum = true; }
			}
		} catch (Exception e) {
			haveMinimum = false;
			e.printStackTrace();
		}
		
		return haveMinimum;
	}
	
	public static <T> boolean isBetweenLength(T obj, int lengthA, int lengthB) {
		boolean haveMinimum = false;
		
		try {
			if (obj instanceof String) {
				if ( (((String) obj).length() > lengthA) && (((String) obj).length() < lengthB)) { haveMinimum = true; }
			} else if (obj instanceof List) {
				if ( (((List) obj).size() > lengthA) && (((List) obj).size() < lengthB)) { haveMinimum = true; }
			} else if (obj instanceof Map) {
				if ( (((Map) obj).size() > lengthA) && (((Map) obj).size() < lengthB)) { haveMinimum = true; }
			}
		} catch (Exception e) {
			haveMinimum = false;
			e.printStackTrace();
		}
		
		return haveMinimum;
	}
	
	
}
