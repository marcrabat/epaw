/**
 * Classe Statica con utilidades genericas.
 * @author Marc P�rez - 173287
 * @author Marc Rabat - 172808
 * @author Marc Alcaraz - 183790
 *
 */

package utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



public class GeneralUtils {

	public static <T, S> boolean existObjectInMap(Map<T, S> map, T key) {
		return map.containsKey(key);
	}
	
	public static <T, S> boolean addObjectToMap(Map<T, S> map, T key, S value) {
		boolean success = false;
		if (map != null) {
			if (existObjectInMap(map, key) == false) {
				map.put(key, value);
				success = true;
			}
		}
		return success;
	}
	
	public static <T, S> boolean removeObjectToMap(Map<T, S> map, T key, S value) {
		boolean success = false;
		if (map != null) {
			if (existObjectInMap(map, key) == true) {
				map.remove(key, value);
				success = true;
			}
		}
		return success;
	}
	
	public static <T, S> boolean removeObjectToMap(Map<T, S> map, T key) {
		boolean success = false;
		if (map != null) {
			if (existObjectInMap(map, key) == true) {
				map.remove(key);
				success = true;
			}
		}
		return success;
	}
	
	public static <T, S> S getMapValue(Map<T, S> map, T key) {
		return map.get(key);
	}
	
	public static <T> boolean existObjectInList(List<T> list, T value) {
		boolean exist = false;
		for (int i = 0; i < (list.size()) && (exist == false); i++) {
			if (list.get(i).equals(value) == true) {
				exist = true;
			}
		}
		return exist;
	}
	
	public static <T> boolean addObjectToList(List<T> list, T value) {
		boolean success = false;
		if (list != null) {
			if (existObjectInList(list, value) == false) {
				list.add(value);
				success = true;
			}
		}
		return success;
	}
	
	public static <T> boolean removeObjectToList(List<T> list, T value) {
		boolean success = false;
		if (list != null) {
			if (list.size() > 0) {
				if (existObjectInList(list, value) == true) {
					list.remove(value);
					success = true;
				}
			}
		}
		return success;
	}
	
	public static <T> boolean addObjectToListWithRepeatPossibility(List<T> list, T value) {
		boolean success = false;
		try {
			if (list != null) {
				list.add(value);
				success = true;
			}
		} catch(Exception e) {
			success = false;
		}
		return success;
	}
	
	public static <T> T getObjectInList(List<T> list, T value) {
		boolean founded = false;
		T result = null;
		try {
			for (int i = 0; i < (list.size()) && (founded == false); i++) {
				if (list.get(i).equals(value) == true) {
					result = list.get(i);
					founded = true;
				}
			}
		} catch(Exception e) {
			result = null;
		}
		return result;
	}
	
	public static List<String> processSimpleText(String text) {
        List<String> tokens = Arrays.asList(text.toLowerCase().replaceAll("[^a-z0-9']", " ").split("\\s+"));

        ArrayList<String> terms = new ArrayList<>();
        for (String token : tokens) {
            //if (token.length() > 3) {
        		terms.add(token);
            //}
        }

        return terms;
    }
	
	public static List<String> processSimpleTextRemovingStopWords(String text, List<String> stopWords) {
        String clearText = text.toLowerCase().replaceAll("[^a-z0-9']", " ");
        clearText = clearText.replaceAll("'", " ");
		List<String> tokens = Arrays.asList(clearText.split("\\s+"));

        ArrayList<String> terms = new ArrayList<>();
        for (String token : tokens) {
        	
    		if (existObjectInList(stopWords, token) == false) {
        		terms.add(token);
    		}

        }

        return terms;
    }
	
	public static String[] tokenize(String txt, String splitter) {
		StringTokenizer tokenizer = new StringTokenizer(txt, splitter);
		String[] result = new String[tokenizer.countTokens()];
		List<String> tokenize = new ArrayList<String>();

		 while(tokenizer.hasMoreTokens() == true){
			 String token = tokenizer.nextToken();
			 if (token != null) {
				 tokenize.add(token);
			 }
         }
		
		return tokenize.toArray(result);
	}
	
	public static String[] split(String txt, String splitter) {
		try {
			return txt.split(splitter);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static List<String> splitList(String txt, String splitter) {
		return Arrays.asList(split(txt, splitter));
	}
	
	public static String concatArrayOfString(String[] array, String concatener) {
		String concat = "";
		if (array != null) {
			for (String s : array) { concat += s + concatener + " "; }
			if (concat.equals("") == false) {
				concat = concat.substring(0, concat.length() - (concatener.length() + 1));
			}
		}
		return concat;
	}
	
	public static String concatListOfString(List<String> list, String concatener) {
		String concat = "";
		if (list != null) {
			for (String s : list) { concat += s + concatener + " "; }
			if (concat.equals("") == false) {
				concat = concat.substring(0, concat.length() - (concatener.length() + 1));
			}
		}
		return concat;
	}
	
	public static <T> List<T> getListFromResultSet(ResultSet resultSet, Class<T> genericClass) {
		List<T> listFilled = new ArrayList<T>();
		try {
			while(resultSet.next()) {
				resultSet.previous();
				T obj = genericClass.newInstance();
				fillFromResultSet(resultSet, obj);
				listFilled.add(obj);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listFilled;
	}
	
	public static <T> boolean fillFromResultSet(ResultSet rs, T obj) {
		boolean filled = true;
		try{
			Class c = Class.forName(obj.getClass().getName());
			Field[] fields = c.getDeclaredFields();
			
			while(rs.next()) {
			
				for(Field field : fields){
					
					String name = field.getName();
					field.setAccessible(true);
					
					if (field.getName().equals("serialVersionUID") == false) {
					
						if (rs.getObject(name) != null) {
							if(field.getType().isAssignableFrom(Boolean.TYPE)){
								
								boolean value = rs.getBoolean(name);
					            field.set(obj, value);
					            
							} else if(field.getType().isAssignableFrom(String.class)){
								
								String value = rs.getString(name).trim();
					            field.set(obj, value);
					            
							} else if (field.getType().isAssignableFrom(Integer.TYPE)){
								
								Integer value = rs.getInt(name);
					            field.set(obj, value);
					            
							} else if(field.getType().isAssignableFrom(Double.TYPE)){
								
								double value = rs.getDouble(name);
					            field.set(obj, value);
					            
							} else if(field.getType().isAssignableFrom(Float.TYPE)){
								
								float value = rs.getFloat(name);
					            field.set(obj, value);
					            
							} else if(field.getType().isAssignableFrom(Date.class)){
								
								Date value = rs.getDate(name);
					            field.set(obj, value);
			
							} else if (field.getType().isAssignableFrom(List.class)) {
								String value = rs.getString(name).trim();
								List<String> list = splitList(value, ",");
					            field.set(obj, list);
							}
						}
					}
					
				}
			}
			
			//Method m = c.getMethod(method, null);
		}catch(Exception e){
			e.printStackTrace();
			filled = false;
		}
		return filled;
	}

}
