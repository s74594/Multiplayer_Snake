package controller;

import java.sql.*;

public class databaseController {
	
	@SuppressWarnings("exports")
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection=DriverManager.getConnection("jdbc:sqlite:playerDB.sqlite"); // insert database path here, otherwise an empty db is being created
			return connection;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}