package controller;

import java.sql.*;
import java.util.Objects;

public class DatabaseController {

    @SuppressWarnings("exports")
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            /*
             * getConnection("jdbc:sqlite:________.sqlite");
             * 								  ^
             * 								  |
             * 								  |- insert database path here,
             * otherwise an empty playerDB is being created
             */
            Connection connection = DriverManager.getConnection("jdbc:sqlite:SQL Lite Database/playerDB.sqlite");

            return connection;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public static Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:src/SQL Lite Database/Snake_System.db";
        String url = "jdbc:sqlite:src/SQL Lite Database/Snake_System.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static String Select_Player_PW(String name)  {
        try{
            String sql = "SELECT players.password FROM players WHERE players.name='" + name+ "'";
            Connection conn = DatabaseController.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            String expectedPW= rs.getString("password");
            conn.close();
            return expectedPW;
        }
        catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static void Insert_Player(String name, String pw, String pw_check) {
        Connection conn = DatabaseController.connect();
        try {
            if (Objects.equals(pw, pw_check)) {
            String sql = "INSERT INTO players (" + "name, password) " +  "VALUES ( '" + name + "', '" + pw + "' );";
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            System.out.println(rs);
                conn.close();
            }
            if (!Objects.equals(pw, pw_check)){
                System.out.println("Passwörter stimmen nicht überein");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



}