package com.playpenbank.model;

public class Transaction {
    private int tid;
    private int accountId;
    private int clientId;
    private String type; // "deposit" o "withdrawal"
    private double amount;
    private String timestamp;

    public Transaction(int tid, int accountId, int clientId, String type, double amount, String timestamp) {
        this.tid = tid;
        this.accountId = accountId;
        this.clientId = clientId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}