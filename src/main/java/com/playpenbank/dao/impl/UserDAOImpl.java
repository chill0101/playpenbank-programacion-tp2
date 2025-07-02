package com.playpenbank.dao.impl;

import com.playpenbank.dao.UserDAO;
import com.playpenbank.dto.UserDTO;
import com.playpenbank.database.Connection;
import com.playpenbank.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserDTO findById(int id) { //Find a single user by id -- used in the login process
        String sql = "SELECT * FROM users WHERE id = ?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("userType")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al encontrar usuario", e);
        }
        return null;
    }

    @Override
    public List<UserDTO> findAll() { //Find all users in the database
        List<UserDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new UserDTO(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("userType")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error al encontrar usuarios", e);
        }
        return users;
    }

    @Override
    public boolean save(UserDTO user) { //Save a new user in the database -- used in the registration process
        String sql = "INSERT INTO users (dni, password, name, lastName, userType) VALUES (?, ?, ?,  ?, ?)";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getDni());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getUserType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar usuario", e);
        }
    }

    @Override
    public boolean update(UserDTO user) { //Update an existing user in the database
        String sql = "UPDATE users SET dni=?, password=?, name=?, lastName=?,  userType=? WHERE id=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getDni());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getUserType());
            stmt.setInt(6, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar usuario", e);
        }
    }

    @Override
    public boolean delete(int id) { // Delete a user from the database -- currently not used in the application
        String sql = "DELETE FROM users WHERE id=?";
        try (java.sql.Connection conn = Connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar usuario", e);
        }
    }
}