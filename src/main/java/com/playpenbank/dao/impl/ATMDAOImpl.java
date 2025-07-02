package com.playpenbank.dao.impl;

import com.playpenbank.dao.ATMDAO;
import com.playpenbank.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.playpenbank.exception.DatabaseException;

public class ATMDAOImpl implements ATMDAO {
    @Override
    public double getBalance() { // Get the current balance of the ATM -- used to display the ATM balance in the employee panel
        String sql = "SELECT balance FROM atm WHERE id = 1";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener balance", e);
        }
        return 0.0;
    }

    @Override
    public boolean updateBalance(double newBalance) { // Update the balance of the ATM
        String sql = "UPDATE atm SET balance = ? WHERE id = 1";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener balance", e);
        }
    }
}