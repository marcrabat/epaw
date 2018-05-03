package database;

import java.sql.*;

public class userDAO {
	private Connection connection;
	private Statement statement;

public userDAO() throws Exception {
String user = "mysql";
String password="lab2";
Class.forName("com.mysql.jdbc.Driver").newInstance();
connection=DriverManager.getConnection("jdbc:mysql://localhost/ts1?user="+user+"&password="+password);
statement=connection.createStatement();
}

	// execute queries
	public ResultSet executeSQL(String query) throws SQLException {
		return statement.executeQuery(query);
	}
	
	public void disconnectBD() throws SQLException {
		statement.close();
		connection.close();
	}
	
	//TODO: NewUsers inserts
}