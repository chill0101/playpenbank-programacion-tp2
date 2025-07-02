package com.playpenbank.dao;

/**
 * ATMDAO interface for managing ATM operations.
 * BASIC Methods to get and update the ATM balance. => The play is to implement this interface in a class that interacts with the database.
 */
public interface ATMDAO {
    double  getBalance();
    boolean updateBalance( double newBalance );
}