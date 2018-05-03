package database;

import java.sql.*;
import utils.BD;

public class UserDAO {
	private BD bd;
	//private Statement statement;

	public UserDAO() {
		this.bd = new BD(BD.url, BD.user, BD.password);
	}
	
}