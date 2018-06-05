/**
 * Clase Statica que contiene utilidades para validar campos o parametros recividos por parametros.
 * @author Marc Pérez - 173287
 *
 */

package utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationUtils {
	
	public static final String REGEX_EMAIL = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";

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
				} else if (obj instanceof List || obj instanceof Map) {
					result = ValidationUtils.haveMinimumLength(obj, 0);
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
				if (((String) obj).length() <= length) { haveMinimum = true; }
			} else if (obj instanceof List) {
				if (((List) obj).size() <= length) { haveMinimum = true; }
			} else if (obj instanceof Map) {
				if (((Map) obj).size() <= length) { haveMinimum = true; }
			} else if (obj instanceof Number) {
				if (((Number) obj).floatValue() <= length) { haveMinimum = true; }
			}
		} catch (Exception e) {
			haveMinimum = false;
			e.printStackTrace();
		}
		
		return haveMinimum;
	}
	
	public static <T> boolean haveMaxLength(T obj, int length) {
		boolean haveMax = false;
		
		try {
			if (obj instanceof String) {
				if (((String) obj).length() >= length) { haveMax = true; }
			} else if (obj instanceof List) {
				if (((List) obj).size() >= length) { haveMax = true; }
			} else if (obj instanceof Map) {
				if (((Map) obj).size() >= length) { haveMax = true; }
			} else if (obj instanceof Number) {
				if (((Number) obj).floatValue() >= length) { haveMax = true; }
			}
		} catch (Exception e) {
			haveMax = false;
			e.printStackTrace();
		}
		
		return haveMax;
	}
	
	public static <T> boolean isBetweenLength(T obj, int lengthA, int lengthB) {
		boolean isBetween = false;
		
		try {
			if (obj instanceof String) {
				
				if ( ((String) obj).length() >= lengthA && ((String) obj).length() <= lengthB ) {
					isBetween = true;
				}
				
			} else if (obj instanceof List) {
				
				if ( ((List) obj).size() >= lengthA && ((List) obj).size() <= lengthB ) {
					isBetween = true;
				}
				
			} else if (obj instanceof Map) {
				
				if ( ((Map) obj).size() >= lengthA && ((Map) obj).size() <= lengthB ) {
					isBetween = true;
				}
				
			} else if (obj instanceof Number) {
				
				if ( ((Number) obj).floatValue() >= lengthA && ((Number) obj).floatValue() <= lengthB ) {
					isBetween = true;
				}
				
			}
		} catch (Exception e) {
			isBetween = false;
			e.printStackTrace();
		}
		
		return isBetween;
	}
	
	public static boolean isPatternMatches(String pattern, String txt) {
		return Pattern.matches(pattern, txt);
	}
	
	public static <T> boolean objPropertiesNotHaveValue(T obj) {
		boolean isEmpty = false;
		try{
			Class c = Class.forName(obj.getClass().getName());
			Field[] fields = c.getDeclaredFields();

			for(Field field : fields) {
				
				if ((field.get(obj) instanceof String)
						|| (field.get(obj) instanceof List) || (field.get(obj) instanceof Map)) {
					
					if ((ValidationUtils.isNull(field.get(obj)) == true) 
							|| (ValidationUtils.isEmpty(obj)) == true) {
						isEmpty = true;
						break;
					}
					
				} else {
					if (ValidationUtils.isNull(field.get(obj)) == true) {
						isEmpty = true;
						break;
					}
				}
			}

		} catch(Exception e) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
}
