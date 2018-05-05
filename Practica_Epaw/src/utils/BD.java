package utils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class BD {

	public static final String url = "jdbc:mysql://localhost/epawTwitter";
	public static final String user = "mysql";
	public static final String password = "prac";

	private Connection connection;

	public BD() {
		this.connection = null;
	}

	public BD(String url, String user, String password) {
		try {
			this.connection = this.createConnection(url, user, password);
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

	private Connection createConnection(String url, String user, String password) throws SQLException, IllegalAccessException, ClassNotFoundException, Exception {
		Connection con = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			con = null;
		}
		return con;
	}

	public void disconnectBD() throws SQLException {
		this.connection.close();
	}

	public int executeSQL(String sql) {
		int succes = 0;
		try {
			if (this.connection != null) {
				Statement st = this.connection.createStatement();
				succes = st.executeUpdate(sql);
				st.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return succes;
	}

	/**
	 * Devuelve un objeto ResultSet que contiene "una foto" de la consulta lanzada,
	 * permitiendo acceder a sus valores, por fila i columna.
	 */
	public ResultSet getResultSet(String sql) {
		ResultSet rs = null;
		try {
			if (this.connection != null) {
				Statement st = this.connection.createStatement();
				rs = st.executeQuery(sql);
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * No se si te funcionara, Es un poco experimental, para ahorrar un poco de
	 * escritura, por que no iva a crear una copia de los metodos del ResultSet.
	 */
	public <T> Class<T> getValue(ResultSet set, String column, T returnType, String method) {
		Class<T> res = null;

		try {
			Class c = Class.forName(set.getClass().getName());
			Method m = c.getMethod(method, null);
			res = (Class<T>) res.cast(m.invoke(set, column));
		} catch (Exception e) {
			res = null;
		}

		return res;
	}
}
