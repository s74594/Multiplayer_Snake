package controller;

import java.sql.*;

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



}