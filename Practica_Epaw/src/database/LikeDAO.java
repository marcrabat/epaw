package database;

import java.sql.*;

public class LikeDAO {
	//public static final String TABLE_NAME = "Likes";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_TWEET_ID = "tweetID";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { COLUMN_USER, COLUMN_TWEET_ID};
	

	public LikeDAO() {
		this.tableName = "Likes";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
			
			if (this.bd.getConnection() == null) {
				this.bd = null;
			}
			
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public int countTweetLikes(int tweetID) {
		int numberOfLikes = 0;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS numberOfLikes FROM " + this.tableName;
				sql += " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + ";";
				
				System.out.println("------------ LikeDAO.java ------------ SQL EXIST: " + sql);
				
				this.bd.executeQuery(sql);
				while(this.bd.getResultSet().next()) {
					numberOfLikes = this.bd.getResultSet().getInt("numberOfLikes");
				}
				this.bd.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			numberOfLikes = 0;
		}
		return numberOfLikes;
	}
	
	public boolean checkUserLike(int tweetID, String username) {
		boolean checkUserLike = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS numberOfLikes FROM " + this.tableName;
				sql += " WHERE " + COLUMN_TWEET_ID + " = '" + tweetID + "' AND " + COLUMN_USER + " = '" + username + "';";
				
				System.out.println("------------ LikeDAO.java ------------ SQL EXIST: " + sql);
				
				this.bd.executeQuery(sql);
				int userLike = 0;
				while(this.bd.getResultSet().next()) {
					userLike = this.bd.getResultSet().getInt("numberOfLikes");
				}
				if(userLike!=0) checkUserLike = true;
				this.bd.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			checkUserLike = false;
		}
		return checkUserLike;
	}
	
	public boolean insertUserLike(int tweetID, String username) {
		
		boolean insert = false;
		if (this.bd != null) {
			
			String sql = "INSERT INTO " + this.tableName + " (" + COLUMN_TWEET_ID + ", ";
			sql += COLUMN_USER +")";
			sql += " VALUES ('" + tweetID + "',";
			sql += "'" + username + "');";
			
			System.out.println("------------ LikeDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}	
	
	public boolean deleteUserLike(int tweetID, String username) {
		boolean delete = false;
		if (this.bd != null) {

			String sql = "DELETE FROM " + this.tableName + " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + " AND " + COLUMN_USER + " = '" + username + "';";
			
			System.out.println("------------ LikeDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result >= 1) ? true : false;
		}
		return delete;
	}
	
	public boolean deleteAllUserLikes(String username) {
		boolean delete = false;
		if (this.bd != null) {

			String sql = "DELETE FROM " + this.tableName + " WHERE " + COLUMN_USER + " = '" + username + "';";
			
			System.out.println("------------ LikeDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result >= 1) ? true : false;
		}
		return delete;
	}
	
}