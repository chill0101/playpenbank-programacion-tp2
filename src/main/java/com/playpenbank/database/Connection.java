package com.playpenbank.database;

import java.sql.DriverManager;
import java.sql.SQLException;
// the toda la vida singleton
public class Connection {
    private static java.sql.Connection connection;
    private static final String URL      = "jdbc:mysql://localhost:3306/playpendb";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    private Connection() {}

    public static java.sql.Connection getConnection() throws SQLException {
        if ( connection == null || connection.isClosed() ) {
            connection = DriverManager.getConnection( URL, USER, PASSWORD );
        }
        return connection;
    }
}