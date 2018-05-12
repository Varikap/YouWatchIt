package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	private static final String DATABASE = "localhost:3306/YouWatchIt";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";
	
	private static Connection connection;
	
	public static void open() {
		try {
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/YouWatchIt?user=root&password=");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close() {
		try {
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
