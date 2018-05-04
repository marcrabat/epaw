package database;

import java.sql.*;

import models.BeanUser;
import utils.BD;
import utils.GeneralUtils;

public class UserDAO {
	private String tableName;
	private BD bd;
	private String[] fields = { "user", "name", "surname", "birthDate", "password",
								"description", "gender", "youtubeChannelID", "twitchChannelID",
								"gamesGenres", "userConsoles", "mail" };
	//private Statement statement;

	public UserDAO() {
		this.tableName = "registeredusers"; //"User";
		try {
			this.bd = new BD(BD.url, BD.user, BD.password);
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public boolean existUser(String user, String mail) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) FROM " + this.tableName;
				sql += " WHERE user = '" + user + "' and mail = '" + mail;
				int result = this.bd.getResultSet(sql).getInt(0);
				exist = (result == 1) ? true : false;
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
			
			String sql = "INSERT INTO " + this.tableName + " (" + GeneralUtils.concatArrayOfString(fields, ",") + ")";
			sql += " VALUES ('" + user.getUser() + ", '" + user.getName() + ", " + user.getSurname() + "', ";
			sql += "'" + user.getBirthDate() + ", '" + user.getPassword() + ", " + user.getDescription() + "', ";
			sql += "'" + user.getGender() + ", '" + user.getYoutubeChannelID() + ", " + user.getTwitchChannelID() + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getGameGenres(), ",") + "', ";
			sql += "'" + GeneralUtils.concatListOfString(user.getUserConsoles(), ",") + "', ";
			sql += "'" + user.getMail() + ");";
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
}