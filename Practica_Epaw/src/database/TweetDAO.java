package database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import models.BeanTweet;
import utils.BD;
import utils.GeneralUtils;
import utils.ValidationUtils;
import database.FeedbackDAO;

public class TweetDAO {
	//public static final String TABLE_NAME = "Tweets";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String COLUMN_TWEET_ID = "tweetID";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_LIKES = "likes";
	public static final String COLUMN_MESSAGE = "message";
	public static final String COLUMN_PUBLISH_DATE = "publishDate";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { COLUMN_TWEET_ID, COLUMN_AUTHOR, COLUMN_LIKES,
								COLUMN_MESSAGE, COLUMN_PUBLISH_DATE };
	
	//private Statement statement;

	public TweetDAO() {
		this.tableName = "Tweets";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
			
			if (this.bd.getConnection() == null) {
				this.bd = null;
			}
			
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public BeanTweet returnTweet(int tweetID) {
		BeanTweet tweet = null;
		if (this.existTweet(tweetID) == true) {
			String sql = "SELECT * FROM " + this.tableName;
			sql += " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + ";";
			
			this.bd.executeQuery(sql);
			ResultSet rs = this.bd.getResultSet();
			GeneralUtils.fillFromResultSet(rs, tweet);
			
			this.bd.close();
		}
		return tweet;
	}
	
	public boolean existTweet(int tweetID) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + ";";
				
				System.out.println("------------ TweetDAO.java ------------ SQL EXIST: " + sql);
				
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

	public boolean insertTweet(BeanTweet tweet) {
		
		boolean insert = false;
		if (this.bd != null) {
			
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = Calendar.getInstance().getTime();

			String sql = "INSERT INTO " + this.tableName + " (" + COLUMN_AUTHOR + ", " + COLUMN_LIKES + ", ";
			sql += COLUMN_MESSAGE + ", " + COLUMN_PUBLISH_DATE +")";
			sql += " VALUES ('" + tweet.getAuthor() + "', 0, ";
			sql += "'" + tweet.getMessage() + "', '" + formatDate.format(date) + "');";
			
			System.out.println("------------ TweetDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
	public boolean deleteTweet(int tweetID) {
		boolean delete = false;
		if (this.bd != null) {

			String sql = "DELETE FROM " + this.tableName + " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + "';";
			
			System.out.println("------------ TweetDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result == 1) ? true : false;
		}
		return delete;
	}
	
	public boolean updateUserAllInfo(BeanTweet tweet) {
		boolean update = false;
		if (this.bd != null) {
			boolean allValuesNull = true;
			String sql = "UPDATE " + this.tableName + " SET ";
			
			if (ValidationUtils.isEmpty(tweet.getAuthor()) == false) {
				sql += COLUMN_AUTHOR + " = '" + tweet.getAuthor() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(tweet.getLikes()) == false) {
				sql += COLUMN_LIKES + " = " + tweet.getLikes() + ", ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(tweet.getPublishDate()) == false) {
				tweet.setPublishDate(tweet.getPublishDate());
				sql += COLUMN_PUBLISH_DATE + " = '" + tweet.getPublishDate() + "', ";
				allValuesNull = false;
			}
			
			if (ValidationUtils.isEmpty(tweet.getMessage()) == false) {
				sql += COLUMN_MESSAGE + " = '" + tweet.getMessage() + "', ";
				allValuesNull = false;
			}
			
			sql = sql.substring(0, (sql.length() - ", ".length()));
			
			sql += " WHERE " + COLUMN_TWEET_ID + " = '" + tweet.getTweetID() + ";";
			
			System.out.println("------------ TweetDAO.java ------------ SQL UPDATE: " + sql);

			if (allValuesNull == false) {	
				int result = this.bd.executeSQL(sql);
				update = (result == 1) ? true : false;
			}
		}
		return update;
	}
	
	public <T> boolean updateTweetField(int tweetID, String fieldName, T value) {
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
			sql += " WHERE " + COLUMN_TWEET_ID + " = " + tweetID + ";";
			
			System.out.println("------------ TweetDAO.java ------------ SQL UPDATE ONE FIELD: " + sql);
			
			int result = this.bd.executeSQL(sql);
			update = (result == 1) ? true : false;

		}
		return update;
	}
	
}