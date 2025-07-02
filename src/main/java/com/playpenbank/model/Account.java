package com.playpenbank.model;


public class Account {

    private int id;
    private String accountNumber;
    private double balance;
    private int clientId;
    private String status; // "active", "inactive", "blocked"

    public Account(int id, String accountNumber, double balance,  int clientId, String status) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.clientId = clientId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("Importe incorrecto.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Importe incorrecto o saldo insuficiente.");
        }
    }


}
