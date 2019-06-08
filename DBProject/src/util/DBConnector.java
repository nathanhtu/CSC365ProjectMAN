package util;

import java.sql.*;

public class DBConnector {
	private Connection conn;
	private final String hostname = "jdbc:mysql://csc365.toshikuboi.net/sec05group05";
	private final String username = "sec05group05";
	private final String password = "group05@sec05";

	public DBConnector() {
		conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(hostname, username, password);
			System.out.println("Connection successful.");
		} catch (Exception e) {
			System.err.println("Cannot connect to server.");
		} finally {
			if (conn == null) {
				try {
					conn.close();
					System.out.println ("Database connection terminated");
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public Connection getConnection() {
		return this.conn;
	}
}
