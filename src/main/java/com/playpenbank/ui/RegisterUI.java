package com.playpenbank.ui;

import com.playpenbank.dto.UserDTO;
import com.playpenbank.service.ClientService;
import com.playpenbank.exception.UserAlreadyTakenException;
import com.playpenbank.ui.screen.AsciiArt;

import java.util.Scanner;

/**
 * Class that handles the user interface for registering a new user in the Playpen Bank system.
 * It prompts the user for their name, last name, DNI, and password, and validates the input.
 * If the registration is successful, it confirms the registration; otherwise, it throws an exception.
 */

public class RegisterUI {
    private final ClientService clientService = new ClientService();

    public void register() throws UserAlreadyTakenException {
        Scanner scanner = new Scanner(System.in); // Initialize the scanner for user input
        // Just normal text in/outs
        AsciiArt.printOptionFrame("Nombre");
        String name = scanner.nextLine();
        AsciiArt.printOptionFrameEnd();

        AsciiArt.printOptionFrame("Apellido");
        String lastName = scanner.nextLine();
        AsciiArt.printOptionFrameEnd();

        String dni;
        do {
            AsciiArt.printOptionFrame("DNI");
            dni = scanner.nextLine();
            AsciiArt.printOptionFrameEnd();
            if (!dni.matches("\\d{7,8}")) { // REGEX to ensure DNI is 7 or 8 digits
                AsciiArt.printSmallFrame("El DNI debe tener 7 u 8 dígitos numéricos.");
            }
        } while (!dni.matches("\\d{7,8}"));  // Loop until a valid password is entered

        String password;
        do {
            AsciiArt.printOptionFrame("Clave (4 dígitos)");
            password = scanner.nextLine();
            AsciiArt.printOptionFrameEnd();
            if (!password.matches("\\d{4}")) { // REGEX to ensure password is exactly 4 digits
                AsciiArt.printSmallFrame("La clave debe ser exactamente 4 dígitos numéricos.");
            }
        } while (!password.matches("\\d{4}")); // Loop until a valid password is entered

        UserDTO user = new UserDTO(0, dni, password, name, lastName, "client");
        // Important! We use the clientService to register the user. The DTOs are used to transfer data between layers.
        boolean ok = clientService.registerClient(user);
        if (ok) {
            AsciiArt.printSmallFrame("¡Registramos tu usuario! Ahora eres parte de nuestra familia, PARA SIEMPRE.");
        } else { // If registration fails, throw an exception --> exception/UserAlreadyTakenException
            throw new UserAlreadyTakenException("Error al registrar el usuario. Intente nuevamente.");
        }
    }
}