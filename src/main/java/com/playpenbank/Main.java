package com.playpenbank;

import com.playpenbank.database.SetupDatabase;
import com.playpenbank.dto.UserDTO;
import com.playpenbank.exception.UserAlreadyTakenException;
import com.playpenbank.service.ClientService;
import com.playpenbank.dto.UserDTO;
import com.playpenbank.ui.ClientMenu;
import com.playpenbank.ui.EmployeeMenu;
import com.playpenbank.ui.LoginUI;
import com.playpenbank.ui.RegisterUI;
import com.playpenbank.database.SetupDatabase;
import com.playpenbank.ui.window.MainFrameWindow;

import java.util.Scanner;

import com.playpenbank.ui.screen.AsciiArt;

public class Main {
    public static void main(String[] args) throws UserAlreadyTakenException {
        SetupDatabase.initialize();

        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrameWindow().setVisible(true);
        });


        // Show welcome banner
//        System.out.println("\n");
//        AsciiArt.printWelcomeBanner();

//        Scanner scanner = new Scanner(System.in);
//        LoginUI loginUI = new LoginUI();
//        RegisterUI registerUI = new RegisterUI();
//
//        while (true) {
//            //Show main menu -- here I use the AsciiArt class to print kind of a frame
//            AsciiArt.printFrame(
//     "\n\n\n\n\n" +
//            " ⋖---Bienvenido a PlayPen---⋗\n\n" +
//            "       1. Iniciar sesión\n" +
//                    "\n"+
//            "       2. Registrarse\n" +
//                    "\n"+
//            "       0. Salir"
//            );
//            int option = -1;
//            while (true) {
//                AsciiArt.printOptionFrame(" Seleccione una opción");
//                String input = scanner.nextLine();
//                try {
//                    option = Integer.parseInt(input);
//                    if (option == 0 || option == 1 || option == 2) {
//                        break; // Exit the loop if a valid option is entered
//                    } else {
//                        AsciiArt.printSmallFrame("Opción inválida. Ingrese 0, 1 o 2.");
//                    }
//                } catch (NumberFormatException e) {
//                    AsciiArt.printSmallFrame("Entrada inválida. Por favor, ingrese un número.");
//                }
//            }
//            AsciiArt.printOptionFrameEnd();
//            if (option == 1) {
//                UserDTO user = loginUI.login(); // Call the login method from LoginUI => ui.LoginUI.java
//                if (user != null) {
//                    if ("employee".equalsIgnoreCase(user.getUserType())) { // Check if the user is an employee
//                        new EmployeeMenu().show(); // Show the employee menu in that case
//                    } else if ("client".equalsIgnoreCase(user.getUserType())) { // Check if the user is a client
//                        new ClientMenu().show(user); // Show the client menu in that case
//                    } else {
//                        AsciiArt.printSmallFrame("Tipo de usuario desconocido --> Error garrafal");
//                    }
//                }
//            } else if (option == 2) {
//                try {
//                    registerUI.register();
//                } catch (UserAlreadyTakenException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else if (option == 0) { // Exit option - close the program
//                AsciiArt.printSmallFrame("Sesión finalizada. ¡Gracias por usar Playpen Bank!");
//                break;
//            } else {
//                AsciiArt.printSmallFrame("Opción inválida!");
//            }
//        }
    }
}