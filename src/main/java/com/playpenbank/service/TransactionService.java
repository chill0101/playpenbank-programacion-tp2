package com.playpenbank.service;

import com.playpenbank.dao.TransactionDAO;
import com.playpenbank.dao.impl.TransactionDAOImpl;
import com.playpenbank.dto.TransactionDTO;

import java.util.*;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAOImpl();

    // Method to save a transaction => TransactionDTO
    public List<TransactionDTO> getMovementsByClientId(int clientId) { // Returns a list of transactions for a specific client - we use this with the clientMenu
        List<TransactionDTO> all = transactionDAO.findAll(); // Fetch all transactions from the DAO
        List<TransactionDTO> result = new ArrayList<>(); // Create a new list to hold transactions for the specified client
        for (TransactionDTO t : all) { // Iterate through all transactions
            if (t.getClientId() == clientId) { // Check if the transaction belongs to the client
                result.add(t); // add => If it does, add it to the result list
            }
        }
        return result;
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionDAO.findAll(); // Returns a list of all transactions
    }

    public void printMostFrequentTransactionType() {
        List<TransactionDTO> all = transactionDAO.findAll();
        if (all.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }
        java.util.Map<String, Integer> counts = new java.util.HashMap<>();
        for (TransactionDTO t : all) {
            counts.put(t.getType(), counts.getOrDefault(t.getType(), 0) + 1);
        }
        String mostFrequent = null;
        int max = 0;
        for (var entry : counts.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        System.out.println("Actividad más frecuente: " + mostFrequent + " (" + max + " veces)");
    }


    public void printATMStats() {
        List<TransactionDTO> all = transactionDAO.findAll();
        if (all.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }

        int totalTransactions = all.size();
        double totalDeposited = 0;
        double totalWithdrawn = 0;
        int totalTransfers = 0;
        int totalDeposits = 0;
        int totalWithdrawals = 0;
        Map<String, Integer> typeCount = new HashMap<>();
        Set<Integer> activeClients = new HashSet<>();

        for (TransactionDTO t : all) { // Iterate through all transactions
            typeCount.put(t.getType(), typeCount.getOrDefault(t.getType(), 0) + 1);
            activeClients.add(t.getClientId());
            switch (t.getType()) { // Count the types of transactions
                case "deposit":
                    totalDeposited += t.getAmount(); // Sum the amount for deposits
                    totalDeposits++; // Count the number of deposits
                    break;
                case "withdrawal":
                    totalWithdrawn += t.getAmount(); // Sum the amount for withdrawals
                    totalWithdrawals++; // Count the number of withdrawals
                    break;
                case "transfer_in":
                case "transfer_out":
                    totalTransfers++; // Count the number of transfers
                    break;
            }
        }

        String mostFrequentType = null; // Variable to hold the most frequent transaction type
        int max = 0;
        for (var entry : typeCount.entrySet()) { // Iterate through the transaction types and their counts
            if (entry.getValue() > max) {
                max = entry.getValue(); // Update the maximum count and the corresponding type
                mostFrequentType = entry.getKey();
            }
        }

        System.out.println("--- Estadísticas del ATM ---");
        System.out.println("Total de transacciones: " + totalTransactions);
        System.out.println("Tipo más frecuente: " + (mostFrequentType != null ? mostFrequentType : "N/A"));
        System.out.println("Total depositado: $" + totalDeposited);
        System.out.println("Total retirado: $" + totalWithdrawn);
        System.out.println("Total transferencias: " + totalTransfers);
        System.out.println("Total depósitos: " + totalDeposits);
        System.out.println("Total retiros: " + totalWithdrawals);
        System.out.println("Clientes activos: " + activeClients.size());
    } // Could use the sb builder to make this method more readable, but it's not necessary for now

    // In src/main/java/com/playpenbank/service/TransactionService.java

    public String getATMStatsString() {
        List<TransactionDTO> all = transactionDAO.findAll();
        if (all.isEmpty()) {
            return "No transactions recorded.";
        }

        int totalTransactions = all.size();
        double totalDeposited = 0;
        double totalWithdrawn = 0;
        int totalTransfers = 0;
        int totalDeposits = 0;
        int totalWithdrawals = 0;
        Map<String, Integer> typeCount = new HashMap<>();
        Set<Integer> activeClients = new HashSet<>();

        for (TransactionDTO t : all) {
            typeCount.put(t.getType(), typeCount.getOrDefault(t.getType(), 0) + 1);
            activeClients.add(t.getClientId());
            switch (t.getType()) {
                case "deposit":
                    totalDeposited += t.getAmount();
                    totalDeposits++;
                    break;
                case "withdrawal":
                    totalWithdrawn += t.getAmount();
                    totalWithdrawals++;
                    break;
                case "transfer_in":
                case "transfer_out":
                    totalTransfers++;
                    break;
            }
        }

        String mostFrequentType = null;
        int max = 0;
        for (var entry : typeCount.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostFrequentType = entry.getKey();
            }
        }

        return String.format(
                "--- ATM Statistics ---\n" +
                        "Total transactions: %d\n" +
                        "Most frequent type: %s (%d times)\n" +
                        "Total deposited: $%.2f\n" +
                        "Total withdrawn: $%.2f\n" +
                        "Total transfers: %d\n" +
                        "Total deposits: %d\n" +
                        "Total withdrawals: %d\n" +
                        "Active clients: %d\n",
                totalTransactions,
                mostFrequentType != null ? mostFrequentType : "N/A", max,
                totalDeposited,
                totalWithdrawn,
                totalTransfers,
                totalDeposits,
                totalWithdrawals,
                activeClients.size()
        );
    }

}