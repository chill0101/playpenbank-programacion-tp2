// src/main/java/com/playpenbank/model/playpenATM.java
package com.playpenbank.model;

import com.playpenbank.dao.ATMDAO;
import com.playpenbank.dao.impl.ATMDAOImpl;
import com.playpenbank.dao.AccountDAO;
import com.playpenbank.dao.impl.AccountDAOImpl;
import com.playpenbank.dao.TransactionDAO;
import com.playpenbank.dao.impl.TransactionDAOImpl;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.dto.TransactionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlaypenATM {
    private static PlaypenATM instance;
    private final int id;
    private double balance;
    private final ATMDAO atmDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    private PlaypenATM() {
        this.id = 1;
        this.atmDAO = new ATMDAOImpl();
        this.accountDAO = new AccountDAOImpl();
        this.transactionDAO = new TransactionDAOImpl();
        this.balance = atmDAO.getBalance();
    }

    public static PlaypenATM getInstance() {
        if (instance == null) {
            instance = new PlaypenATM();
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void fill(double amount) {
        if (amount > 0) {
            this.balance += amount;
            atmDAO.updateBalance(this.balance);
        } else {
            throw new IllegalArgumentException("Importe incorrecto.");
        }
    }

    // Extracción gestionada por el cajero
    public String withdrawFromClient(int clientId, double amount) { // maybe should check if null first
        if (amount <= 0) {
            return "Importe incorrecto.";
        }
        AccountDTO account = getActiveAccountByClientId(clientId);
        if ("blocked".equalsIgnoreCase(account.getStatus())) {
            return "OPERACIÓN BLOQUEADA. " + com.playpenbank.util.PlayPenUtil.fraseRandom();
        }
        if (account == null) {
            return "No se encontró cuenta activa asociada.";
        }
        if (account.getBalance() < amount) {
            return "Saldo insuficiente en la cuenta.";
        }
        if (this.balance < amount) {
            return "El cajero no tiene suficiente efectivo.";
        }
        account.setBalance(account.getBalance() - amount);
        boolean updated = accountDAO.update(account);
        if (updated) {
            this.balance -= amount;
            atmDAO.updateBalance(this.balance);
            // Registrar transacción
            TransactionDTO tx = new TransactionDTO(
                    0,
                    account.getId(),
                    clientId,
                    "withdrawal",
                    amount,
                    now()
            );
            transactionDAO.save(tx);
            return "OK";
        } else {
            return "Error al retirar.";
        }
    }

    private AccountDTO getActiveAccountByClientId(int clientId) {
        for (AccountDTO acc : accountDAO.findAll()) {
            if (acc.getClientId() == clientId ) {
                return acc;
            }
        }
        return null;
    }

    public String depositFromClient(int clientId, double amount) {
        if (amount <= 0) {
            return "Importe incorrecto.";
        }
        AccountDTO account = getActiveAccountByClientId(clientId);
        if (account == null) {
            return "No se encontró cuenta activa asociada.";
        }
        account.setBalance(account.getBalance() + amount);
        boolean updated = accountDAO.update(account);
        if (updated) {
            this.balance += amount;
            atmDAO.updateBalance(this.balance);
            // Registrar transacción
            TransactionDTO tx = new TransactionDTO(
                    0,
                    account.getId(),
                    clientId,
                    "deposit",
                    amount,
                    now()
            );
            transactionDAO.save(tx);
            return "OK";
        } else {
            return "Error al depositar.";
        }
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}