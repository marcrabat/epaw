package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.BD;
import utils.GeneralUtils;

public class RelationshipDAO {
	//public static final String TABLE_NAME = "Relationship";
	
	// ---- COLUMS OF TABLE IN DATABSE -----
	public static final String COLUMN_USERA = "userA";
	public static final String COLUMN_USERB = "userB";
	// ----- END COLUMS OF TABLE IN DATABASE ----
	
	private String tableName;
	private BD bd;
	private String[] fields = { COLUMN_USERA, COLUMN_USERB };
	
	//private Statement statement;

	public RelationshipDAO() {
		this.tableName = "Relationship";
		try {
			this.bd = new BD(BD.URL, BD.USER, BD.PASSWORD);
			
			if (this.bd.getConnection() == null) {
				this.bd = null;
			}
			
		} catch (Exception e) {
			this.bd = null;
		}
		
	}
	
	public boolean existRelationship(String userA, String userB) {
		boolean exist = false;
		try {
			if (this.bd != null) {
				String sql = "SELECT COUNT(*) AS exist FROM " + this.tableName;
				sql += " WHERE " + COLUMN_USERA + " = '" + userA + "'";
				sql += " AND " + COLUMN_USERB + " = '" + userB + "';";
				
				System.out.println("------------ RelationshipDAO.java ------------ SQL EXIST: " + sql);
				
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

	public boolean insertRelationship(String userA, String userB) {
		
		boolean insert = false;
		if (this.bd != null) {

			String sql = "INSERT INTO " + this.tableName + " (" + GeneralUtils.concatArrayOfString(fields, ",") + ")";
			sql += " VALUES ('" + userA + "', '" + userB + "');";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL INSERT: " + sql);
			
			int result = this.bd.executeSQL(sql);
			insert = (result == 1) ? true : false;
		}
		return insert;
	}
	
	public boolean deleteRelationship(String userA, String userB) {
		boolean delete = false;
		if (this.bd != null) {
			String sql = "DELETE FROM " + this.tableName + " WHERE " + COLUMN_USERA + " = '" + userA + "'";
			sql += " AND " + COLUMN_USERB + " = '" + userB + "';";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL DELETE: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result >= 1) ? true : false;
		}
		return delete;
	}
	
	public boolean deleteUserRelationship(String user) {
		boolean delete = false;
		if (this.bd != null) {
			String sql = "DELETE FROM " + this.tableName;
			sql += " WHERE " + COLUMN_USERA + " = '" + user + "'";
			sql += " OR " + COLUMN_USERB + " = '" + user + "';";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL DELETE USER RELATIONSHIPS: " + sql);

			int result = this.bd.executeSQL(sql);
			delete = (result >= 1) ? true : false;
		}
		return delete;
	}
	
	public List<String> getFollowers(String user) {
		List<String> followers = new ArrayList<String>();
		if (this.bd != null) {
			String sql = "SELECT " + COLUMN_USERA + " FROM " + this.tableName;
			sql += " WHERE " + COLUMN_USERB + " = '" + user + "';";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL FOLLOWERS: " + sql);

			
			this.bd.executeQuery(sql);
			ResultSet rs = this.bd.getResultSet();
			try {
				while(rs.next()) {
					String follower = rs.getString(COLUMN_USERA);
					followers.add(follower);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.bd.close();

		}
		return followers;
	}
	
	public List<String> getFollowing(String user) {
		List<String> following = new ArrayList<String>();
		if (this.bd != null) {
			String sql = "SELECT " + COLUMN_USERB + " FROM " + this.tableName;
			sql += " WHERE " + COLUMN_USERA + " = '" + user + "';";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL FOLLOWING: " + sql);

			this.bd.executeQuery(sql);
			ResultSet rs = this.bd.getResultSet();
			try {
				while(rs.next()) {
					String userId = rs.getString(COLUMN_USERB);
					following.add(userId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.bd.close();
		}
		return following;
	}
	
	
	public List<String> getAllUsersNotFollowed(String user) {
		List<String> allUsersNotFollowed = new ArrayList<String>();
		if (this.bd != null) {
			String sql = "SELECT DISTINCT user FROM Users WHERE user NOT IN( SELECT " + COLUMN_USERB + " FROM " + this.tableName;
			sql += " WHERE " + COLUMN_USERA + " = '" + user + "') AND user <> '" + user + "';";
			
			System.out.println("------------ RelationshipDAO.java ------------ SQL FOLLOWING: " + sql);

			this.bd.executeQuery(sql);
			ResultSet rs = this.bd.getResultSet();
			try {
				while(rs.next()) {
					String userId = rs.getString("user");
					allUsersNotFollowed.add(userId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.bd.close();
		}
		return allUsersNotFollowed;
	}
	
}