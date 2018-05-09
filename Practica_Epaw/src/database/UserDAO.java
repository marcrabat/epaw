package database;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import models.BeanUser;
import utils.BD;
import utils.GeneralUtils;
import utils.ValidationUtils;

public class UserDAO {
	//public static final String TABLE_NAME = "registeredusers"; // "User";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String USER = "user";
	public static final String MAIL = "mail";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String BIRTH_DATE = "birthDate";
	public static final String PASSWORD = "password";
	public static final String DESCRIPTION = "description";
	public static final String GENDER = "gender";
	public static final String YOUTUBE_CHANNEL_ID = "youtubeChannelID";
	public static final String TWITCH_CHANNEL_ID = "twitchChannelID";
	public static final String GAME_GENRES = "gameGenres";
	public static final String USER_CONSOLES = "userConsoles";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { USER, MAIL, NAME, SURNAME, BIRTH_DATE, PASSWORD,
								DESCRIPTION, GENDER, YOUTUBE_CHANNEL_ID, TWITCH_CHANNEL_ID,
								GAME_GENRES, USER_CONSOLES };
	
	//private Statement statement;

	public UserDAO() {
		this.tableName = "registeredusers"; //"User";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public BeanUser returnUser(String fieldName, String value) {
		BeanUser user = null;
		if (this.existUser(fieldName, value) == true) {
			String sql = "SELECT * FROM " + this.tableName;
			sql += " WHERE " + fieldName + " = '" + value + "';";
			ResultSet rs = this.bd.getResultSet(sql);
			if (rs != null) {
				GeneralUtils.fillFromResultSet(rs, user);
				System.out.println(user.toString());
			}
		}
		return user;
	}
	
	public boolean existUser(String fieldName, String value) {
		boolean exist = false;
		try {
			if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + fieldName + " = '" + value + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL EXIST: " + sql);
				
				ResultSet rs = this.bd.getResultSet(sql);
				rs.next();
				System.out.println(String.valueOf(this.bd.getValue(rs, "exist", Integer.class, "getInt")));
				int result = (rs != null) ? rs.getInt("exist") : 1;
				exist = (result >= 1) ? true : false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			exist = true;
		}
		return exist;
	}

	public boolean insertUser(BeanUser user) {
		
		boolean insert = false;
		if (this.bd != null) {

			String sql = "INSERT INTO " + this.tableName + " (" + GeneralUtils.concatArrayOfString(fields, ",") + ")";
			sql += " VALUES ('" + user.getUser() + "', '" + user.getName() + "', '" + user.getSurname() + "', ";
			sql += "'" + user.getBirthDate() + "', '" + user.getPassword() + "', '" + user.getDescription() + "', ";
			sql += "'" + user.getGender() + "', '" + user.getYoutubeChannelID() + "' , '" + user.getTwitchChannelID() + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getGameGenres(), "' ,") + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getUserConsoles(), "' ,") + "', ";
			sql += "'" + user.getMail() + "');";
			
			System.out.println("------------ UserDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
	public boolean deleteUser(String fieldName, String toDelete) {
		boolean delete = false;
		if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {
			String sql = "DELETE FROM " + this.tableName + " WHERE " + fieldName + " LIKE '" + toDelete + "';";
			System.out.println("------------ UserDAO.java ------------ SQL DELETE: " + sql);
			
			int result = this.bd.executeSQL(sql);
			delete = (result == 1) ? true : false;
		}
		return delete;
	}
	
	public boolean updateUserAllInfo(BeanUser user) {
		boolean update = false;
		if (this.bd != null) {
			boolean allValuesNull = true;
			String sql = "UPDATE " + this.tableName + " SET ";
			
			if (ValidationUtils.isEmpty(user.getName()) == false) {
				sql += NAME + " = '" + user.getName() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getSurname()) == false) {
				sql += SURNAME + " = '" + user.getSurname() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getBirthDate()) == false) {
				sql += BIRTH_DATE + " = '" + user.getBirthDate() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getPassword()) == false) {
				sql += PASSWORD + " = '" + user.getPassword() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getDescription()) == false) {
				sql += DESCRIPTION + " = '" + user.getDescription() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getGender()) == false) {
				sql += GENDER + " = '" + user.getGender() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getYoutubeChannelID()) == false) {
				sql += YOUTUBE_CHANNEL_ID + " = '" + user.getYoutubeChannelID() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getTwitchChannelID()) == false) {
				sql += TWITCH_CHANNEL_ID + " = '" + user.getTwitchChannelID() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getGameGenres()) == false) {
				sql += GAME_GENRES + " = '" + GeneralUtils.concatListOfString(user.getGameGenres(), ",") + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getUserConsoles()) == false) {
				sql += USER_CONSOLES + " = '" + GeneralUtils.concatListOfString(user.getUserConsoles(), ",") + "', ";
				allValuesNull = false;
			}
			
			sql = sql.substring(0, (sql.length() - ", ".length()));
			
			sql += " WHERE " + USER + " = '" + user.getUser() + "' AND " + MAIL + " = '" + user.getMail() + "';";
			
			System.out.println("------------ UserDAO.java ------------ SQL UPDATE: " + sql);
			
			/* old Version, si estiguesim segurs que les dades no arriven buides o null, no hi hauria problema.
			sql += " SET name = '" + user.getName() + "', surname = '" + user.getSurname() + "', ";
			sql += "birthDate = '" + user.getBirthDate() + "', password = '" + user.getPassword() + "', ";
			sql += "description = '" + user.getDescription() + "', gender = '" + user.getGender() + "', ";
			sql += "youtubeChannelID = '" + user.getYoutubeChannelID() + "', ";
			sql += "twitchChannelID = '" + user.getTwitchChannelID() + "', ";
			sql += "gameGenres = '" + GeneralUtils.concatListOfString(user.getGameGenres(), ",") + "', ";
			sql += "userConsoles = '" + GeneralUtils.concatListOfString(user.getUserConsoles(), ",") + "'";
			sql += " WHERE user = '" + user.getUser() + "' and mail = '" + user.getMail() + "';";
			*/
			
			if (allValuesNull == false) {	
				int result = this.bd.executeSQL(sql);
				update = (result == 1) ? true : false;
			}
		}
		return update;
	}
	
	public <T> boolean updateUserField(String user, String mail, String fieldName, T value) {
		boolean update = false;
		if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), value)) == true) {

			String sql = "UPDATE " + this.tableName;
			sql += " SET " + fieldName + " = ";
		
			if (value instanceof Number) {
				sql += value + " ";
			} else if (value instanceof List) {
				sql += "'" + GeneralUtils.concatListOfString((List) value, ",") + "' ";
			} else {
				sql += "'" + value + "' ";
			}
			sql += " WHERE " + USER + " = '" + user + "' AND " + MAIL + " = '" + mail + "';";
			
			System.out.println("------------ UserDAO.java ------------ SQL UPDATE ONE FIELD: " + sql);
			
			int result = this.bd.executeSQL(sql);
			update = (result == 1) ? true : false;

		}
		return update;
	}
	
}