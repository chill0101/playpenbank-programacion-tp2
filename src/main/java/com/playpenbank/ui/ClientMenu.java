package com.playpenbank.ui;

import com.playpenbank.dto.UserDTO;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.dto.TransactionDTO;
import com.playpenbank.model.PlaypenATM;
import com.playpenbank.service.AccountService;
import com.playpenbank.service.TransactionService;
import com.playpenbank.ui.screen.AsciiArt;

import java.util.List;
import java.util.Scanner;


/**
 * Class that handles the client menu UI for the Playpen Bank application.
 * It allows clients to view their account balance, transactions,
 * deposit, withdraw, and transfer money.
 */
public class ClientMenu {
    private final AccountService accountService = new AccountService(); // Define the AccountService instance to handle account-related operations
    private final TransactionService transactionService = new TransactionService(); // Define the TransactionService instance to handle transaction-related operations

    public void show(UserDTO user) { // UserDTO
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        do {
            AsciiArt.printFrame(
                    "\n\n" +
                            "        ⋖--- Menú Cliente ---⋗\n\n" +
                            "        1. Ver saldo\n" +
                            "\n" +
                            "        2. Ver movimientos\n" +
                            "\n" +
                            "        3. Depositar dinero\n" +
                            "\n" +
                            "        4. Retirar dinero\n" +
                            "\n" +
                            "        5. Transferir a otra cuenta\n" +
                            "\n" +
                            "        0. Salir"
            );
            AsciiArt.printOptionFrame("Seleccione una opción:");
            try {
                option = Integer.parseInt(scanner.nextLine());
                AsciiArt.printOptionFrameEnd();
            } catch (Exception e) {
                AsciiArt.printSmallFrame("Opción inválida. Ingrese un número.");
                continue;
            }

            switch (option) {
                case 1: // VER SALDO
                    AccountDTO account = accountService.getAccountByClientId(user.getId()); // Retrieve the account associated with the client
                    if (account != null) { // representing the account
                        AsciiArt.printSmallFrame("Saldo actual: $" + String.format("%.2f", account.getBalance())); // Print the current balance of the account
                    } else {
                        AsciiArt.printSmallFrame("No se encontró cuenta asociada.");
                    }
                    break;
                case 2: // VER MOVIMIENTOS
                    List<TransactionDTO> movimientos = transactionService.getMovementsByClientId(user.getId()); // Retrieve the transactions associated with the client using the TransactionService
                    // I used <TransactionDTO> as the type for the list to represent the transactions, which is a DTO (Data Transfer Object) that contains transaction details.
                    if (movimientos.isEmpty()) { // Retrieve the transactions associated with the client
                        AsciiArt.printSmallFrame("No hay movimientos.");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (TransactionDTO t : movimientos) {
                            sb.append(String.format(
                                    "%-12s $%10.2f  |  %s\n",
                                    t.getType(),
                                    t.getAmount(),
                                    t.getTimestamp().replace('T', ' ').substring(0, 19)
                            ));
                        }
                        AsciiArt.printFrame(sb.toString());
                    }

                    break;
                case 3: // DEPOSITAR DINERO
                    AsciiArt.printOptionFrame("Ingrese monto a depositar:");
                    try { // dep = deposit
                        double dep = Double.parseDouble(scanner.nextLine()); // Parse the deposit amount from user input
                        AsciiArt.printOptionFrameEnd();
                        String depResult = PlaypenATM.getInstance().depositFromClient(user.getId(), dep); // Deposit the amount using the PlaypenATM singleton instance.
                        // For that I used the method depositFromClient, which is a method that handles the deposit operation for the client.
                        if ("OK".equals(depResult)) {
                            AccountDTO updatedAccount = accountService.getAccountByClientId(user.getId()); // Retrieve the updated account after the deposit.
                            // I used the method getAccountByClientId to retrieve the account associated with the client after the deposit.
                            AsciiArt.printSmallFrame("Depósito realizado. Monto: $" + String.format("%.2f", dep) +
                                    " | Saldo actual: $" + String.format("%.2f", updatedAccount.getBalance()));
                        } else {
                            AsciiArt.printSmallFrame(depResult);
                        }
                    } catch (Exception e) {
                        AsciiArt.printSmallFrame("Importe inválido.");
                    }
                    break;
                case 4: // RETIRAR DINERO
                    AsciiArt.printOptionFrame("Ingrese monto a retirar:");

                    try {
                        double ret = Double.parseDouble(scanner.nextLine());
                        String result = PlaypenATM.getInstance().withdrawFromClient(user.getId(), ret); // Withdraw the amount using the PlaypenATM singleton instance.
                        AsciiArt.printOptionFrameEnd();
                        if ("OK".equals(result)) {
                            AccountDTO updatedAccount = accountService.getAccountByClientId(user.getId());
                            AsciiArt.printSmallFrame("Retiro realizado. Monto: $" + String.format("%.2f", ret) +
                                    " | Saldo actual: $" + String.format("%.2f", updatedAccount.getBalance()));
                        } else {
                            AsciiArt.printSmallFrame(result);
                        }
                    } catch (Exception e) {
                        AsciiArt.printSmallFrame("Importe inválido.");
                    }
                    break;
                case 5: // TRANSFERIR A OTRA CUENTA -> This was difficult
                    AsciiArt.printOptionFrame("Ingrese número de cuenta destino:");

                    String destAccount = scanner.nextLine(); // Read the destination account  from user input => you have to know the account number to transfer money
                    // For demo use 111-78194 (client id 3)
                    AsciiArt.printOptionFrameEnd();
                    AsciiArt.printOptionFrame("Ingrese monto a transferir:");

                    try {
                        double monto = Double.parseDouble(scanner.nextLine());
                        boolean ok = accountService.transfer(user.getId(), destAccount, monto); // Transfer money from the client's account to the destination account using the AccountService
                        // I used the method transfer from the AccountService to handle the transfer operation.
                        // ok as boolean to check if the transfer was successful
                        AsciiArt.printOptionFrameEnd();
                        if (ok) {
                            AccountDTO updatedAccount = accountService.getAccountByClientId(user.getId()); // Retrieve the updated account after the transfer
                            // I used the method getAccountByClientId to retrieve the account associated with the client after the transfer.
                            AsciiArt.printSmallFrame("Transferencia realizada. Saldo actual: $" +
                                    String.format("%.2f", updatedAccount.getBalance()));
                        } else {
                            AsciiArt.printSmallFrame("No se pudo realizar la transferencia. Verifique los datos y el saldo.");
                        }
                    } catch (Exception e) {
                        AsciiArt.printSmallFrame("Importe inválido.");
                    }
                    break;
                case 0:
                    AsciiArt.printSmallFrame("Cerrando sesión...");
                    break;
                default:
                    AsciiArt.printSmallFrame("Opción inválida.");
            }
        } while (option != 0);
    }
}