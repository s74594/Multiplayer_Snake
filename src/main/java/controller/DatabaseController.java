package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;



public class DatabaseController {


	@SuppressWarnings("exports")
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			/*
			 * getConnection("jdbc:sqlite:________.sqlite"); ^ | |- insert database path
			 * here, otherwise an empty playerDB is being created
			 */
			Connection connection = DriverManager.getConnection("jdbc:sqlite:SQL Lite Database/playerDB.sqlite");

			return connection;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@SuppressWarnings("exports")
	public static Connection connect() {
		// SQLite connection string
		// String url = "jdbc:sqlite:src/SQL Lite Database/Snake_System.db";
		String url = "jdbc:sqlite:src/SQL Lite Database/Snake_System.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static String Select_Player_PW(String name) {
		try {
			String sql = "SELECT players.password FROM players WHERE players.name='" + name + "'";
			Connection conn = DatabaseController.connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String expectedPW = rs.getString("password");
			conn.close();
			return expectedPW;
		} catch (SQLException e) {
			return e.getMessage();
		}
	}

	public static String Insert_Player(String name, String pw, String pw_check) {
		Connection conn = DatabaseController.connect();
		try {
			if (Objects.equals(pw, pw_check)) {
				String sql = "INSERT INTO players (" + "name, password) " + "VALUES ( '" + name + "', '" + pw + "' );";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				System.out.println(rs);
				conn.close();
				return "Neuer Spieler angelegt";
			} else if (!Objects.equals(pw, pw_check)) {
				conn.close();
				return "Passwörter stimmen nicht überein";
			}
		} catch (SQLException e) {
			String x = e.getMessage();
			return x;
		}
		return "Notwendige Returnmeldung";
	}

	public static List<String[]>  GetHighscore() {
		List<String[]> lottoList = new LinkedList<String[]>();
		try {
			String sql = "SELECT Players.name, Highscore.points, Highscore.date_of_score from Highscore,Players WHERE Highscore.player_id = Players.player_id ORDER BY points desc";
			Connection conn = DatabaseController.connect();
			Statement stmt = conn.createStatement();
			ResultSet set = stmt.executeQuery(sql);

		//	ResultSet set = state.executeQuery("SELECT * FROM numberLotto");

			while(set.next())
			{
				String[] currentRow = new String[] {set.getString(1),
						set.getString(2),
						set.getString(3)
				};
				lottoList.add(currentRow);
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return lottoList;
	}

}