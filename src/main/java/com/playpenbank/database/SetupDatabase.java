package com.playpenbank.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {
// This class is responsible for initializing the database schema and inserting initial data if necessary.
    public static void initialize() {
        try ( 
             Connection conn = com.playpenbank.database.Connection.getConnection();
             Statement stmt = conn.createStatement() // Statement para ejecutar consultas SQL
        ) {
            // Create table => users
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "dni VARCHAR(20) NOT NULL," +
                            "password CHAR(4) NOT NULL," +
                            "name VARCHAR(100) NOT NULL," +
                            "lastName VARCHAR(100) NOT NULL," +
                            "userType VARCHAR(20) NOT NULL)"
            );

            // Create table => accounts
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS accounts (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "accountNumber VARCHAR(20) NOT NULL," +
                            "balance DOUBLE NOT NULL," +
                            "clientId INT NOT NULL," +
                            "status ENUM('active','inactive','blocked') NOT NULL)"
            );

            // Create table => transactions
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                            "tid INT AUTO_INCREMENT PRIMARY KEY," +
                            "accountId INT NOT NULL," +
                            "clientId INT NOT NULL," +
                            "type VARCHAR(20) NOT NULL," +
                            "amount DOUBLE NOT NULL," +
                            "timestamp VARCHAR(30) NOT NULL)"
            );

            // Create table => atm
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS atm (" +
                            "id INT PRIMARY KEY," +
                            "balance DOUBLE NOT NULL)"
            );



            // Insertar datos de prueba solo si no existen
            stmt.executeUpdate(
                    "INSERT IGNORE INTO atm (id, balance) VALUES (1, 100000.00)"
            );
            stmt.executeUpdate(
                    "INSERT IGNORE INTO users (id, dni, password, name, lastName, userType) VALUES " +
                            "(1, '11111111', '1234', 'Elliot', 'Alderson',  'employee')," +
                            "(2, '22222222', '1234', 'Saul', 'Goodman',  'client')," +
                            "(3, '33333333', '1234', 'Gordon', 'Freeman',  'client')"
            );
            stmt.executeUpdate(
                    "INSERT IGNORE INTO accounts (id, accountNumber, balance, clientId, status) VALUES " +
                            "(1, '111-63122', 5000.0, 2, 'active')," +
                            "(2, '111-99923', 3000.0, 3, 'active')"

            );
            stmt.executeUpdate(
                    "INSERT IGNORE INTO transactions (tid, accountId, clientId, type, amount, timestamp) VALUES " +
                            "(1, 1, 2, 'deposit', 5000.0, '2024-06-01T10:00:00Z')"
            );

            //System.out.println( "DEBUG => Database initialized successfully." );

        } catch ( SQLException e ) {
            e.printStackTrace(); // Print the stack trace for debugging purposes
        }
    }
}