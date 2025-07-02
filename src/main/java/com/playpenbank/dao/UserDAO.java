package com.playpenbank.dao;

import com.playpenbank.dto.UserDTO;
import java.util.List;

/**
 * UserDAO interface for managing user operations.
 * BASIC Methods to find, save, update, and delete users.
 * The play is to implement this interface in a class that interacts with the database.
 */
public interface UserDAO {
    UserDTO findById( int id );

    List<UserDTO> findAll();

    boolean save    ( UserDTO user );
    boolean update  ( UserDTO user );
    boolean delete  ( int id );
}