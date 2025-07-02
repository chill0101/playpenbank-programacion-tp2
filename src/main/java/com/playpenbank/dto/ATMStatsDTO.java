package com.playpenbank.dto;

public class ATMStatsDTO {
    private int id;
    private String date;
    private int totalTransactions;
    private String mostFrequentType;
    private double totalDeposited;
    private double totalWithdrawn;
    private int totalTransfers;
    private int totalDeposits;
    private int totalWithdrawals;
    private int activeClients;

    // Constructor (Does not use
    // )
    public ATMStatsDTO(
                        int id, // Meant to store the stats in the database => but in the future, for now its just unused
                        String date,
                        int totalTransactions,      // Total number of transactions in the ATM
                        String mostFrequentType,    // Most frequent transaction type in the ATM (deposit, withdrawal, transfer)
                        double totalDeposited,      // Total amount deposited in the ATM
                        double totalWithdrawn,      // Total amount withdrawn from the ATM
                        int totalTransfers,         // Total number of transfers in the ATM
                        int totalDeposits,          // Total number of deposits in the ATM
                        int totalWithdrawals,       // Total number of withdrawals in the ATM
                        int activeClients           // Total number of active clients using the ATM
    ) {
                        this.id = id;
                        this.date = date;
                        this.totalTransactions = totalTransactions;
                        this.mostFrequentType = mostFrequentType;
                        this.totalDeposited = totalDeposited;
                        this.totalWithdrawn = totalWithdrawn;
                        this.totalTransfers = totalTransfers;
                        this.totalDeposits = totalDeposits;
                        this.totalWithdrawals = totalWithdrawals;
                        this.activeClients = activeClients;
    }

    // Getters - these are used to access the private fields
    // Don't need setters
    public int getId() { return id; }
    public String getDate() { return date; }
    public int getTotalTransactions() { return totalTransactions; }
    public String getMostFrequentType() { return mostFrequentType; }
    public double getTotalDeposited() { return totalDeposited; }
    public double getTotalWithdrawn() { return totalWithdrawn; }
    public int getTotalTransfers() { return totalTransfers; }
    public int getTotalDeposits() { return totalDeposits; }
    public int getTotalWithdrawals() { return totalWithdrawals; }
    public int getActiveClients() { return activeClients; }
}