package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.BD;
import utils.GeneralUtils;

public class FeedbackDAO {
	//public static final String TABLE_NAME = "Feedback";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String COLUMN_TWEET_1 = "tweet1";
	public static final String COLUMN_TWEET_2 = "tweet2";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { COLUMN_TWEET_1, COLUMN_TWEET_2 };
	
	//private Statement statement;

	public FeedbackDAO() {
		this.tableName = "Feedback";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
			
			if (this.bd.getConnection() == null) {
				this.bd = null;
			}
			
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public boolean existFeedback(int tweetID1, int tweetID2) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + COLUMN_TWEET_1 + " = " + tweetID1;
				sql += " AND " + COLUMN_TWEET_2 + " = " + tweetID2 + ";";
				
				System.out.println("------------ FeedbackDAO.java ------------ SQL EXIST: " + sql);
				
				this.bd.executeQuery(sql);
				while(this.bd.getResultSet().next()) {
					int result = this.bd.getResultSet().getInt("exist");
					exist = (result >= 1) ? true : false;
				}
				this.bd.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			exist = true;
		}
		return exist;
	}

	public boolean associateTweet(int tweetID, int relatedTweetID) {
		
		boolean insert = false;
		if (this.bd != null) {

			String sql = "INSERT INTO " + this.tableName + " (" + GeneralUtils.concatArrayOfString(fields, ",") + ")";
			sql += " VALUES (" + String.valueOf(tweetID) + ", " + String.valueOf(relatedTweetID) + ");";
			
			System.out.println("------------ FeedbackDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
	public boolean deleteAssociated(int tweetID, int relatedTweetID) {
		boolean delete = false;
		if (this.bd != null) {
			String sql = "DELETE FROM " + this.tableName;
			sql += " WHERE " + COLUMN_TWEET_1 + " = '" + String.valueOf(tweetID) + "'";
			sql += " AND " + COLUMN_TWEET_2 + " = '" + String.valueOf(relatedTweetID) + "';";
			
			System.out.println("------------ FeedbackDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result == 1) ? true : false;
		}
		return delete;
	}
	
	public boolean deleteTweetAssociations(int tweetID) {
		boolean delete = false;
		if (this.bd != null) {
			String sql = "DELETE FROM " + this.tableName;
			sql += " WHERE " + COLUMN_TWEET_1 + " = " + String.valueOf(tweetID) + ";";
			
			System.out.println("------------ FeedbackDAO.java ------------ SQL DELETE ASSOCIATION: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result == 1) ? true : false;
		}
		return delete;
	}
	
	public List<Integer> getAssociated(int tweetID) {
		List<Integer> associated = new ArrayList<Integer>();
		if (this.bd != null) {
			String sql = "SELECT " + COLUMN_TWEET_2 + " FROM " + this.tableName;
			sql += " WHERE " + COLUMN_TWEET_1 + " = " + String.valueOf(tweetID) + ";";
			
			System.out.println("------------ FeedbackDAO.java ------------ SQL ASSOCIATED: " + sql);

			this.bd.executeQuery(sql);
			ResultSet rs = this.bd.getResultSet();
			try {
				while(rs.next()) {
					int associatedTweet = rs.getInt(COLUMN_TWEET_2);
					associated.add(associatedTweet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.bd.close();
		}
		return associated;
	}

}