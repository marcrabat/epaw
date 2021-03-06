package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.BeanUser;
import utils.GeneralUtils;
import utils.ValidationUtils;
import database.RelationshipDAO;

public class UserDAO {
	//public static final String TABLE_NAME = "Users";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_MAIL = "mail";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SURNAME = "surname";
	public static final String COLUMN_BIRTH_DATE = "birthDate";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_YOUTUBE_CHANNEL_ID = "youtubeChannelID";
	public static final String COLUMN_TWITCH_CHANNEL_ID = "twitchChannelID";
	public static final String COLUMN_GAME_GENRES = "gameGenres";
	public static final String COLUMN_USER_CONSOLES = "userConsoles";
	public static final String COLUMN_IS_ADMIN = "isAdmin";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { COLUMN_USER, COLUMN_MAIL, COLUMN_NAME, COLUMN_SURNAME,
								COLUMN_BIRTH_DATE, COLUMN_PASSWORD, COLUMN_DESCRIPTION, COLUMN_GENDER, 
								COLUMN_YOUTUBE_CHANNEL_ID, COLUMN_TWITCH_CHANNEL_ID,
								COLUMN_GAME_GENRES, COLUMN_USER_CONSOLES, COLUMN_IS_ADMIN };
	

	public UserDAO() {
		this.tableName = "Users";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
			
			if (this.bd.getConnection() == null) {
				this.bd = null;
			}
			
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public BeanUser returnUser(String fieldName, String value) {
		BeanUser user = new BeanUser();
		if (this.bd != null) {
			if (this.existUserSelectingField(fieldName, value) == true) {
				String sql = "SELECT " + GeneralUtils.concatArrayOfString(this.fields, ",") + " FROM " + this.tableName;
				sql += " WHERE " + fieldName + " = '" + value + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL SELECT USER: " + sql);
				
				this.bd.executeQuery(sql);
				ResultSet rs = this.bd.getResultSet();
				GeneralUtils.fillFromResultSet(rs, user);
				this.bd.close();
			}
		}
		return user;
	}
	
	public boolean existUser(String user, String mail) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + COLUMN_USER + " = '" + user + "'";
				sql += " OR " + COLUMN_MAIL + " = '" + mail + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL EXIST: " + sql);
				
				this.bd.executeQuery(sql);
				while(this.bd.getResultSet().next()) {
					int result = this.bd.getResultSet().getInt("exist");
					exist = (result >= 1) ? true : false;
				}
				this.bd.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			exist = false;
		}
		return exist;
	}
	
	public boolean existUserSelectingField(String fieldName, String value) {
		boolean exist = false;
		try {
			if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + fieldName + " = '" + value + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL EXIST: " + sql);
				
				this.bd.executeQuery(sql);
				while(this.bd.getResultSet().next()) {
					int result = this.bd.getResultSet().getInt("exist");
					exist = (result >= 1) ? true : false;
				}
				this.bd.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			exist = false;
		}
		return exist;
	}

	public boolean insertUser(BeanUser user) {
		
		boolean insert = false;
		if (this.bd != null) {
			
			// Por algun motivo no se parsea correctamente la fecha cuando se le assigna el valor,
			// haciendo esto conseguimos un formato correcto para la BD.
			user.setBirthDate(user.getBirthDate());

			String sql = "INSERT INTO " + this.tableName + " (" + GeneralUtils.concatArrayOfString(this.fields, ",") + ")";
			sql += " VALUES ('" + user.getUser() + "', '" + user.getMail() + "', '" + user.getName() + "', ";
			sql += "'" + user.getSurname() + "', '" + user.getBirthDate() + "', '" + user.getPassword() + "', ";
			sql += "'" + user.getDescription() + "', '" + user.getGender() + "', ";
			sql += "'" + user.getYoutubeChannelID() + "' , '" + user.getTwitchChannelID() + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getGameGenres(), " ,") + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getUserConsoles(), " ,") + "', b'0');";
			
			System.out.println("------------ UserDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
	public boolean deleteUser(String fieldName, String toDelete) {
		boolean delete = false;
		if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {
			String sql = "DELETE FROM " + this.tableName + " WHERE " + fieldName + " = '" + toDelete + "';";
			System.out.println("------------ UserDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result >= 1) ? true : false;
		}
		return delete;
	}
	
	public boolean updateUserAllInfo(BeanUser user) {
		boolean update = false;
		if (this.bd != null) {
			
			String sql = "UPDATE " + this.tableName + " SET ";
			sql += COLUMN_DESCRIPTION + " = '" + user.getDescription() + "', ";
			sql += COLUMN_YOUTUBE_CHANNEL_ID + " = '" + user.getYoutubeChannelID() + "', ";
			sql += COLUMN_TWITCH_CHANNEL_ID + " = '" + user.getTwitchChannelID() + "', ";

			if (ValidationUtils.isEmpty(user.getName()) == false) {
				sql += COLUMN_NAME + " = '" + user.getName() + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getSurname()) == false) {
				sql += COLUMN_SURNAME + " = '" + user.getSurname() + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getBirthDate()) == false) {
				sql += COLUMN_BIRTH_DATE + " = '" + user.getBirthDate() + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getPassword()) == false) {
				sql += COLUMN_PASSWORD + " = '" + user.getPassword() + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getGender()) == false) {
				sql += COLUMN_GENDER + " = '" + user.getGender() + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getGameGenres()) == false) {
				sql += COLUMN_GAME_GENRES + " = '" + GeneralUtils.concatListOfString(user.getGameGenres(), ",") + "', ";
			}
			
			if (ValidationUtils.isEmpty(user.getUserConsoles()) == false) {
				sql += COLUMN_USER_CONSOLES + " = '" + GeneralUtils.concatListOfString(user.getUserConsoles(), ",") + "', ";
			}
			
			sql = sql.substring(0, (sql.length() - ", ".length()));
			
			sql += " WHERE " + COLUMN_USER + " = '" + user.getUser() + "';";
			
			System.out.println("------------ UserDAO.java ------------ SQL UPDATE: " + sql);
			
			int result = this.bd.executeSQL(sql);
			update = (result == 1) ? true : false;

		}
		return update;
	}
	
	public <T> boolean updateUserField(String user, String mail, String fieldName, T value) {
		boolean update = false;
		if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {

			String sql = "UPDATE " + this.tableName;
			sql += " SET " + fieldName + " = ";
		
			if (value instanceof Number) {
				sql += value + " ";
			} else if (value instanceof List) {
				sql += "'" + GeneralUtils.concatListOfString((List) value, ",") + "' ";
			} else {
				sql += "'" + value + "' ";
			}
			sql += " WHERE " + COLUMN_USER + " = '" + user + "' AND " + COLUMN_MAIL + " = '" + mail + "';";
			
			System.out.println("------------ UserDAO.java ------------ SQL UPDATE ONE FIELD: " + sql);
			
			int result = this.bd.executeSQL(sql);
			update = (result == 1) ? true : false;

		}
		return update;
	}
	
	public boolean loginUser(String fieldName, String user, String password) {
		boolean login = false;
		try {
			if ((this.bd != null) && (GeneralUtils.existObjectInList(Arrays.asList(this.fields), fieldName)) == true) {
				String sql = "SELECT COUNT(*) AS login FROM " + this.tableName;
				sql += " WHERE " + fieldName + " = '" + user + "'";
				sql += " AND " + COLUMN_PASSWORD + " = '" + password + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL Login: " + sql);
				
				this.bd.executeQuery(sql);
				while(this.bd.getResultSet().next()) {
					int result = this.bd.getResultSet().getInt("login");
					login = (result >= 1) ? true : false;
				}
				this.bd.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
			login = false;
		}
		return login;
	}
	
}