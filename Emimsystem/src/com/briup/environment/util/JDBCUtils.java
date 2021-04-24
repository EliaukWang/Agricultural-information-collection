package com.briup.environment.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;	
	
	static {
		Properties prop = null;
		InputStream is = null;
		try {
			prop = new Properties();
			is = new FileInputStream("src/jdbc.properties");
			prop.load(is);
			driverClassName = prop.getProperty("driverClassName");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			Class.forName(driverClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JDBCUtils() {}
	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(url, username, password);
	}
	public static void close(Connection conn,Statement stmt) {
		close(conn,stmt,null);
	}
	
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
