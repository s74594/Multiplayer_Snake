package snakeserver.database;

import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DatabaseController {

    /**
     * opens connection to database
     *
     * @return database connection
     */
    public static Connection connect() {
        String url = "jdbc:sqlite:server/src/snakeserver/database/Snake_System.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * retrives password for selected player from database
     *
     * @param name
     * @return users password
     */
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

    /**
     * creates player in database
     *
     * @param name
     * @param pw
     * @param pw_check
     * @return success of operation
     */
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

    /**
     * retrieves data for highscore table from database
     *
     * @return highscore table
     */
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
                String[] currentRow = new String[]{set.getString(1), set.getString(2), set.getString(3)};
                highscoreList.add(currentRow);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return highscoreList;
    }

    /**
     * creates highscore in database
     *
     * @param player_id
     * @param datenow
     * @param points
     */
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
     * retrieves playerID from database
     *
     * @param player_name
     * @return playerID
     */
    public static String getPlayerId(String player_name) {
        Connection conn = DatabaseController.connect();
        System.out.println("SELECT player_id FROM players WHERE name=" + player_name + ";");

        try {
            String sql = "SELECT players.player_id FROM players WHERE players.name='" + player_name + "';";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String player_ID = rs.getString(1);
            System.out.println(rs);
            conn.close();

            return player_ID.toString();
        } catch (SQLException e) {
            String x = e.getMessage();
            return x;
        }
    }


    /**
     * creates list of played games in database
     *
     * @param player1id
     * @param player2id
     * @param player1_points
     * @param player2_points
     * @param startTime
     * @param datenow
     * @param duration
     */
    public static void setGamedata(String player1id,
                                   String player2id,
                                   int player1_points,
                                   int player2_points,
                                   String startTime,
                                   String datenow,
                                   int duration) {
        Connection conn = DatabaseController.connect();

        try {
            System.out.println(player1id + ", " + player2id +
                    ", " + player1_points + ", " + player2_points + ", " + startTime + ", " + datenow + ", " + duration + ");");

            String sql = "INSERT INTO games( player1_id, player2_id,player1_points,player2_points,start_datetime,end_datetime, duration) VALUES (" + player1id + ", " + player2id +
                    ", " + player1_points + ", " + player2_points + ", " + startTime + ", " + datenow + ", " + duration + ");";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);
            conn.close();
        } catch (SQLException e) {
            String x = e.getMessage();
            System.out.println(e);
        }
    }

    /**
     * Insert a new row into the 'snakecolor' table
     *
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