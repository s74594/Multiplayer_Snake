package model;

import java.sql.*;

import controller.DatabaseController;

public class LoginViewModel {

    Connection connection;

    public LoginViewModel() {

        connection = DatabaseController.Connector();

        if (connection == null) System.exit(1); // DB connection fails
    }

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}