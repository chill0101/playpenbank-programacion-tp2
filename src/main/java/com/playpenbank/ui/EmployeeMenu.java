package com.playpenbank.ui;

import com.playpenbank.dto.TransactionDTO;
import com.playpenbank.model.PlaypenATM;
import com.playpenbank.service.TransactionService;
import com.playpenbank.ui.screen.AsciiArt;

import java.util.List;
import java.util.Scanner;

/**
 * Class that represents the Employee Menu in the Playpen Bank ATM system.
 * It allows employees to view all transactions,
 * load money into the ATM,
 * view ATM statistics,
 * and activate/deactivate the "Playpen Mode" (corralito).
 * This menu is accessible only to employees and provides options => Default for testin 11111111 | 1234
 */
public class EmployeeMenu {
    private final TransactionService transactionService = new TransactionService();
    // This service handles transaction-related operations such as retrieving all transactions and printing ATM statistics.

    public void show() {
        Scanner scanner = new Scanner(System.in);
        int option = 0; // Variable to store the user's menu option input
        do {
            AsciiArt.printFrame(
                    "\n\n" +
                            "        ⋖--- Playpen ATM System ---⋗\n\n" +
                            "\n" +
                            "        1. Ver todas las transacciones\n" +
                            "\n" +
                            "        2. Cargar dinero al ATM\n" +
                            "\n" +
                            "        3. Ver estadísticas del ATM\n" +
                            "\n" +
                            "        0. Salir"
            );
            AsciiArt.printOptionFrame("Seleccione una opción");
            String input = scanner.nextLine();
            AsciiArt.printOptionFrameEnd();
            try {
                option = Integer.parseInt(input);
            } catch (Exception e) {
                AsciiArt.printSmallFrame("Opción inválida. Ingrese un número.");
                continue;
            }

            switch (option) {
                case 1:
                    List<TransactionDTO> trnsct = transactionService.getAllTransactions(); // Retrieve all transactions from the transaction service --> service.TransactionService.getAllTransactions()
                    if (trnsct.isEmpty()) { // Check if there are no transactions
                        AsciiArt.printSmallFrame("No hay transacciones registradas.");
                    } else {
                        StringBuilder sb = new StringBuilder(); // JAVA IS AWESOME! I didn't know I could use StringBuilder like this
                        for (TransactionDTO t : trnsct) {
                            sb.append(t.getType()) // Append the transaction type
                                    .append(" $") // Append the transaction amount
                                    .append(String.format("%.2f", t.getAmount())) // Format the amount to 2 decimal places
                                    .append(" - ") // Append a separator
                                    .append(t.getTimestamp()) // Append the transaction timestamp
                                    .append("\n"); // Append a newline for each transaction
                        } // AND THIS PRINTS A LIST OF ALL TRANSACTIONS IN THE ATM
                        AsciiArt.printOptionFrame(sb.toString());
                        System.out.println(""); // Print an empty line for usability

                    }
                    break;
                case 2:
                    AsciiArt.printOptionFrame("Ingrese monto a cargar");
                    String montoStr = scanner.nextLine();
                    AsciiArt.printOptionFrameEnd();
                    try {
                        double monto = Double.parseDouble(montoStr);
                        PlaypenATM.getInstance().fill(monto); // Load money into the ATM using the PlaypenATM singleton instance -> model.PlaypenATM.getInstance().fill(monto)
                        AsciiArt.printSmallFrame("Dinero cargado al ATM: $" + String.format("%.2f", monto)); // String formatting to ensure 2 decimal places
                    } catch (Exception e) {
                        AsciiArt.printSmallFrame("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    // Puedes adaptar printATMStats para usar AsciiArt si lo deseas
                    transactionService.printATMStats(); // Print ATM statistics using the transaction service -> service.TransactionService.printATMStats()
                    System.out.println(""); // Print an empty line for usability
                    break;
                case 2001: // This is an Easter egg for activating "Playpen Mode" (corralito) 💀💀💀
                    new com.playpenbank.service.AccountService().activatePlaypenMode(); // Activate "Playpen Mode" using the AccountService -> service.AccountService.activatePlaypenMode()
                    AsciiArt.printSmallFrame("MODO CORRALITO ACTIVADO! Todas las cuentas han sido bloqueadas ☠ ☠ ☠");
                    AsciiArt.printTransitionBanner();
                    AsciiArt.printSmallFrame("Esta información es confidencial y por nada en el mundo debe ser divulgada");
                    break;
                case 2002: // This returns the system to normal operation, deactivating "Playpen Mode" (corralito)
                    new com.playpenbank.service.AccountService().deactivatePlaypenMode(); // Deactivate "Playpen Mode" using the AccountService -> service.AccountService.deactivatePlaypenMode()
                    AsciiArt.printSmallFrame("¡MODO CORRALITO DESACTIVADO! Todas las cuentas han sido reactivadas!");
                    AsciiArt.printWelcomeBanner();
                    break;
                case 0:
                    AsciiArt.printSmallFrame("Muchas gracias por usar Playpen Bank. ¡Nos vemos!");
                    break;
                default:
                    AsciiArt.printSmallFrame("Opción inválida. Intente ingresar un número del 0 al 3.");
            }
        } while (option != 0);
    }
}