package controller;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.scene.paint.Color;

public class DatabaseController {

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

	public static List<String[]> GetHighscore() {
		List<String[]> highscoreList = new LinkedList<String[]>();
		try {
			String sql = "SELECT Players.name, Highscore.points, Highscore.date_of_score from Highscore, Players WHERE Highscore.player_id = Players.player_id ORDER BY points desc";
			Connection conn = DatabaseController.connect();
			Statement stmt = conn.createStatement();
			ResultSet set = stmt.executeQuery(sql);

			while (set.next()) {
				// String datum = set.getString(3);
				// Date date = new Date(Long.parseLong(datum));
				String[] currentRow = new String[] { set.getString(1), set.getString(2), set.getString(3) };
				highscoreList.add(currentRow);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return highscoreList;
	}

	public static void Insert_Highscore(String player_id, LocalDateTime datenow, int points) {
		Connection conn = DatabaseController.connect();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatDateTime = datenow.format(formatter);
		try {
			String sql = "INSERT INTO highscore (" + "player_id, date_of_score, points) " + "VALUES ( '" + player_id
					+ "', '" + formatDateTime + "', '" + points + "' );";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(rs);
			conn.close();
		} catch (SQLException e) {
			String x = e.getMessage();
		}
	}

	/**
	 * Insert a new row into the 'snakecolor' table
	 * @param value
	 */
	@SuppressWarnings("exports")
	public void Insert_Custom_Snake_Color(Color value) {
		Connection conn = DatabaseController.connect();

//		String sqlInsertString = "INSERT INTO snakecolor (id, hexcolor) VALUES (1, '" + value + "' )";
		String sqlUpdateString = "UPDATE snakecolor SET hexcolor = '" + value + "' WHERE snakecolor.id = 1;";

		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sqlUpdateString);
			System.out.println(">> " + sqlUpdateString);
			conn.close();
		} catch (SQLException e) {
			System.out.println(">> " + e.getMessage());
		}
	}
}