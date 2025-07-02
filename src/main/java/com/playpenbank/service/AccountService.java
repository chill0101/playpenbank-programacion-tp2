package com.playpenbank.service;

import com.playpenbank.dao.AccountDAO;
import com.playpenbank.dao.impl.AccountDAOImpl;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.dao.TransactionDAO;
import com.playpenbank.dao.impl.TransactionDAOImpl;
import com.playpenbank.dto.TransactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * AccountService class for managing account operations.
 * This class provides methods to retrieve accounts by client id, transfer between accounts, and manage account statuses.
 * It uses AccountDAO and TransactionDAO to interact with the database
 */
public class AccountService {
    private final AccountDAO accountDAO = new AccountDAOImpl();
    private final TransactionDAO transactionDAO = new TransactionDAOImpl();

    public AccountDTO getAccountByClientId(int clientId) {
        List<AccountDTO> accounts = accountDAO.findAll(); // Retrieve all accounts from the database
        for (AccountDTO acc : accounts) {
            if (acc.getClientId() == clientId ) {
                return acc;
            }
        }
        return null;
    }
    public boolean transfer(int fromClientId, String toAccountNumber, double amount) {
        if (amount <= 0) return false;
        AccountDTO from = getAccountByClientId(fromClientId); // Retrieve the account of the client making the transfer_out
        if (from == null || !"active".equalsIgnoreCase(from.getStatus())) return false;

        List<AccountDTO> all = accountDAO.findAll();
        // Find the destination account by account number
        AccountDTO to = null;
        for (AccountDTO acc : all) {
            if (acc.getAccountNumber().equals(toAccountNumber) && "active".equalsIgnoreCase(acc.getStatus())) {
                to = acc; // Check if the account is active
                break;
            }
        }
        if (to == null || from.getId() == to.getId()) return false; // Ensure the destination account exists and is not the same as the source account
        if ("blocked".equalsIgnoreCase(from.getStatus()) || "blocked".equalsIgnoreCase(to.getStatus())) { // Check if either account is blocked
            return false;
        }
        if (from.getBalance() < amount) return false; // Check source account has enough balance

        from.setBalance(from.getBalance() - amount); // Deduct amount from source account
        to.setBalance(to.getBalance() + amount); // Add amount to destination account

        boolean ok1 = accountDAO.update(from); // Update source account in the database
        boolean ok2 = accountDAO.update(to); // Update destination account in the database

        if (ok1 && ok2) { // PURE BUSINESS LOGIC :D
            transactionDAO.save(new TransactionDTO(0, from.getId(), fromClientId, "transfer_out", amount, now())); // Log the transfer_out transaction
            transactionDAO.save(new TransactionDTO(0, to.getId(), to.getClientId(), "transfer_in", amount, now())); // Log the transfer_in transaction
            return true;
        }
        return false;
    }

    public void activatePlaypenMode() { // Method to activate playpen mode by blocking all accounts. JUST USE IT WHEN THE TABLE DEMANDS!
        List<AccountDTO> cuentas = accountDAO.findAll();
        for (AccountDTO acc : cuentas) { // Iterate through all accounts
            if (!"blocked".equalsIgnoreCase(acc.getStatus())) { // Check if the account is not already blocked
                acc.setStatus("blocked"); // Set the account status to blocked
                accountDAO.update(acc);
            }
        }
    }

    public void deactivatePlaypenMode() {
        List<AccountDTO> cuentas = accountDAO.findAll();
        for (AccountDTO acc : cuentas) {
            if ("blocked".equalsIgnoreCase(acc.getStatus())) { // Check if the account is blocked
                acc.setStatus("active"); // Set the account status to active
                accountDAO.update(acc); // Update the account status in the database
            }
        }
    }



    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    } // Get the current date and time in format, I'm traumatized with native date and time handling in Java, so I use this method to get the current date and time in ISO format, which is easy to read and parse later on.
}