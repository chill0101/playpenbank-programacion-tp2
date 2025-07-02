package com.playpenbank.dao;

import com.playpenbank.dto.TransactionDTO;
import java.util.List;

/**
 * TransactionDAO interface for managing transaction operations.
 * BASIC Methods to find, save, update, and delete transactions.
 * The play is to implement this interface in a class that interacts with the database.
 */
public interface TransactionDAO {
    TransactionDTO findById( int tid );
    List<TransactionDTO> findAll();
    boolean save    ( TransactionDTO transaction );
    boolean update  ( TransactionDTO transaction );
    boolean delete  ( int tid );
}