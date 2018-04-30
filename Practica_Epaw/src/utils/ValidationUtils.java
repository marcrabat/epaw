/**
 * Clase Statica que contiene utilidades para validar campos o parametros recividos por parametros.
 * @author Marc Pérez - 173287
 *
 */
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
	
}
