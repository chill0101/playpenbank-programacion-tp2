package com.playpenbank.dao.impl;

import com.playpenbank.dao.TransactionDAO;
import com.playpenbank.dto.TransactionDTO;
import com.playpenbank.database.Connection;
import com.playpenbank.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public TransactionDTO findById(int tid) { // Find a single transaction by its ID
        String sql = "SELECT * FROM transactions WHERE tid = ?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TransactionDTO(
                        rs.getInt("tid"),
                        rs.getInt("accountId"),
                        rs.getInt("clientId"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("timestamp")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al encontrar transacción", e);
        }
        return null;
    }

    @Override
    public List<TransactionDTO> findAll() { // Find all transactions in the database -- used to display transaction history
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                transactions.add(new TransactionDTO(
                        rs.getInt("tid"),
                        rs.getInt("accountId"),
                        rs.getInt("clientId"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al encontrar transacciones", e);
        }
        return transactions;
    }

    @Override
    public boolean save(TransactionDTO transaction) { // Save a new transaction in the database (transactions table)
        String sql = "INSERT INTO transactions (accountId, clientId, type, amount, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getAccountId());
            stmt.setInt(2, transaction.getClientId());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5, transaction.getTimestamp());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar transacción", e);
        }
    }

    @Override
    public boolean update(TransactionDTO transaction) { // Update an existing transaction in the database -- currently not used, but could be useful for future features!
        String sql = "UPDATE transactions SET accountId=?, clientId=?, type=?, amount=?, timestamp=? WHERE tid=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getAccountId());
            stmt.setInt(2, transaction.getClientId());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5, transaction.getTimestamp());
            stmt.setInt(6, transaction.getTid());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar transacción", e);
        }
    }

    @Override
    public boolean delete(int tid) { // Delete a transaction from the database by its ID -- currently not used in the application, but could be useful for future features!
        String sql = "DELETE FROM transactions WHERE tid=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tid);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar transacción", e);
        }
    }
}