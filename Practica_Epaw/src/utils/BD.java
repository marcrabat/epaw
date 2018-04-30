import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BD {
	
	public Connection connection;
	
	public BD() {
		this.connection = null;
	}
	
	public BD(String url, String user, String password) {
		this.connection = this.createConnection(url, user, password);
	}
	
	public setConnection(Connection connection) { this.connection = connection; }
	
	private Connection createConnection(String url, String user, Stirng password) throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
	public int executeSQL(String sql) {
		int succes = 0;
		try {
			if (this.connection != null) {
				Statement st = this.connection.createStatement();
				succes = st.executeUpdate(sql);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return succes;
	}
	
	/**
	 * Devuelve un objeto ResultSet que contiene "una foto" de la consulta lanzada, 
	 * permitiendo acceder a sus valores, por fila i columna.
	 */
	public ResultSet getResultSet(String sql) {
		Statement st = this.connection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
	/* No se si te funcionara, Es un poco experimental, para ahorrar un poco de escritura,
		por que no iva a crear una copia de los metodos del ResultSet. */
	public <T> getValue(ResultSet set, String column, T returnType, String method) {
		Class<T> res = null;
		
		try {
			Class c = Class.forName(set.getClass().getName());
			Method m = c.getMethod(method, null);
			res = res.cast(m.invoke(set, column)); 
		} catch (Exception e) {
			res = null;
		}
		
		return res;
	}
}
