package utils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class BD {

	public static final String URL = "jdbc:mysql://localhost/epawTwitter";
	public static final String USER = "root"; //"mysql";
	public static final String PASSWORD = ""; //"prac";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public BD() {
		this.connection = null;
	}

	public BD(String url, String user, String password) {
		try {
			this.createConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			this.connection = null;
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public ResultSet getResultSet() {
		return this.resultSet;
	}
	
	public void openConnection() {
		try {
			if (this.connection != null) {
				if (this.connection.isClosed() == true) {
					this.createConnection(URL, USER, PASSWORD);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			
			if (this.resultSet != null) {
				this.resultSet.close();
			}
			
			if (this.statement != null) {
				this.statement.close();
			}
			
			if (this.connection != null) {
				this.connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createConnection(String url, String user, String password) throws SQLException, IllegalAccessException, ClassNotFoundException, Exception {
		if (this.connection == null || this.connection.isClosed() == true) {
			
			try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				this.connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
				this.connection = null;
			}
		
		}
	}

	public int executeSQL(String sql) {
		int succes = 0;
		try {
			
			this.openConnection();
			this.statement = this.createStatement();
			succes = this.statement.executeUpdate(sql);
			this.statement.close();
			this.connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return succes;
	}
	
	public void executeQuery(String sql) {
		try {

			this.openConnection();
			this.statement = this.createStatement();
			this.resultSet = this.statement.executeQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement createStatement() {
		Statement st = null;
		try {
			if (this.connection != null) {
				
				if (this.statement != null) {
					this.statement.close();
				}
				
				st = this.connection.createStatement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	/*
	 * No se si te funcionara, Es un poco experimental, para ahorrar un poco de
	 * escritura, por que no iva a crear una copia de los metodos del ResultSet.
	 */
	public <T> Class<T> getValue(ResultSet resultSet, String column, T returnType, String method) {
		Class<T> res = null;

		try {
			Class c = Class.forName(resultSet.getClass().getName());
			Method m = c.getMethod(method, new Class[]{String.class});
			res = (Class<T>) res.cast(m.invoke(resultSet, column));
		} catch (Exception e) {
			res = null;
		}

		return res;
	}
}
