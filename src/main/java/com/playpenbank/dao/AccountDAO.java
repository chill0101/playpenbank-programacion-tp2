package com.playpenbank.dao;

import com.playpenbank.dto.AccountDTO;
import java.util.List;

/**
 * AccountDAO interface for managing account operations.
 * BASIC Methods to find, save, update, and delete accounts. => The play is to implement this interface in a class that interacts with the database.
 */
public interface AccountDAO {
    AccountDTO findById( int id );
    List<AccountDTO> findAll();
    boolean save    ( AccountDTO account );
    boolean update  ( AccountDTO account );
    boolean delete  ( int id );
}