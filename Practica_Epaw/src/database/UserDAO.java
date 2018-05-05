package database;

import java.sql.*;
import java.util.Arrays;

import models.BeanUser;
import utils.BD;
import utils.GeneralUtils;
import utils.ValidationUtils;

public class UserDAO {
	private String tableName;
	private BD bd;
	private String[] fields = { "user", "name", "surname", "birthDate", "password",
								"description", "gender", "youtubeChannelID", "twitchChannelID",
								"gameGenres", "userConsoles", "mail" };
	//private Statement statement;

	public UserDAO() {
		this.tableName = "registeredusers"; //"User";
		try {
			this.bd = new BD(BD.url, BD.user, BD.password);
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public BeanUser returnUser(String username, String mail) {
		BeanUser user = null;
		if (this.existUser(username, mail) == true) {
			String sql = "SELECT * FROM " + this.tableName;
			sql += " WHERE user = '" + user + "' and mail = '" + mail + "';";
			ResultSet rs = this.bd.getResultSet(sql);
			if (rs != null) {
				GeneralUtils.fillFromResultSet(rs, user);
				System.out.println(user.toString());
			}
		}
		return user;
	}
	
	public boolean existUser(String user, String mail) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) FROM " + this.tableName;
				sql += " WHERE user = '" + user + "' and mail = '" + mail + "';";
				
				System.out.println("------------ UserDAO.java ------------ SQL EXIST: " + sql);
				
				ResultSet rs = this.bd.getResultSet(sql);
				int result = (rs != null) ? rs.getInt(0) : 1;
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
			String sql = "DELETE FROM " + this.tableName + " WHERE " + this.getFieldNamed(fieldName) + " LIKE '" + toDelete + "';"; //TODO pretty way to take the name of the column?
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
			String sql = "UPDATE " + this.tableName + " SET";
			
			if (ValidationUtils.isEmpty(user.getName()) == false) {
				sql += " name = '" + user.getName() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getSurname()) == false) {
				sql += " surname = '" + user.getSurname() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getBirthDate()) == false) {
				sql += " birthDate = '" + user.getBirthDate() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getPassword()) == false) {
				sql += " password = '" + user.getPassword() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getDescription()) == false) {
				sql += " description = '" + user.getDescription() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getGender()) == false) {
				sql += " gender = '" + user.getGender() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getYoutubeChannelID()) == false) {
				sql += " youtubeChannelID = '" + user.getYoutubeChannelID() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getTwitchChannelID()) == false) {
				sql += " twitchChannelID = '" + user.getTwitchChannelID() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getGameGenres()) == false) {
				sql += " gameGenres = '" + GeneralUtils.concatListOfString(user.getGameGenres(), ",") + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(user.getUserConsoles()) == false) {
				sql += " userConsoles = '" + GeneralUtils.concatListOfString(user.getUserConsoles(), ",") + "', ";
				allValuesNull = false;
			}
			
			sql = sql.substring(0, (sql.length() - ", ".length()));
			
			sql += " WHERE user = '" + user.getUser() + "' and mail = '" + user.getMail() + "';";
			
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
			} else {
				sql += "'" + value + "' ";
			}
			sql += " WHERE user = '" + user + "' and mail = '" + mail + "';";
			
			System.out.println("------------ UserDAO.java ------------ SQL UPDATE ONE FIELD: " + sql);
			
			int result = this.bd.executeSQL(sql);
			update = (result == 1) ? true : false;

		}
		return update;
	}
	
	public String getFieldNamed(String fieldName) {
		for(String field : this.fields) {
			if(field.equals(fieldName)) {
				return field;
			}
		}
		return "";
	}
	
}