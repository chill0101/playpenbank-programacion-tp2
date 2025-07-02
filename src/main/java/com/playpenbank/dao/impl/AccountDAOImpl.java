package com.playpenbank.dao.impl;

import com.playpenbank.dao.AccountDAO;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.database.Connection;
import com.playpenbank.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public AccountDTO findById(int id) { // SELECT by id
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (java.sql.Connection conn = Connection.getConnection(); // Get connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare statement
            stmt.setInt(1, id); // Set parameter
            ResultSet rs = stmt.executeQuery(); // Execute query
            if (rs.next()) {
                return new AccountDTO(
                        rs.getInt("id"),
                        rs.getString("accountNumber"),
                        rs.getDouble("balance"),
                        rs.getInt("clientId"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar cuenta", e);
        }
        return null;
    }

    @Override
    public List<AccountDTO> findAll() { // SELECT all accounts
        List<AccountDTO> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(new AccountDTO(
                        rs.getInt("id"),
                        rs.getString("accountNumber"),
                        rs.getDouble("balance"),
                        rs.getInt("clientId"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener datos de las cuentas", e);
        }
        return accounts;
    }

    @Override
    public boolean save(AccountDTO account) {
        String sql = "INSERT INTO accounts (accountNumber, balance, clientId, status) VALUES (?, ?, ?, ?)";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setDouble(2, account.getBalance());
            stmt.setInt(3, account.getClientId());
            stmt.setString(4, account.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar la cuenta", e);
        }
    }

    @Override
    public boolean update(AccountDTO account) { // UPDATE by id
        String sql = "UPDATE accounts SET accountNumber=?, balance=?, clientId=?, status=? WHERE id=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setDouble(2, account.getBalance());
            stmt.setInt(3, account.getClientId());
            stmt.setString(4, account.getStatus());
            stmt.setInt(5, account.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar la cuenta", e);
        }
    }

    @Override
    public boolean delete(int id) { // DELETE by id -- currently not used in the application, but could be useful for future features!
        String sql = "DELETE FROM accounts WHERE id=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al borrar la cuenta", e);
        }
    }
}