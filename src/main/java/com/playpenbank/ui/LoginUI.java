package com.playpenbank.ui;

import com.playpenbank.dto.UserDTO;
import com.playpenbank.service.UserService;
import com.playpenbank.ui.screen.AsciiArt;

import java.util.Scanner;

/**
 * Class that handles the login UI for the Playpen Bank application.
 * It prompts the user for their document and password,
 * validates the credentials,
 * and returns a UserDTO object if successful.
 */
public class LoginUI {
    private final UserService userService = new UserService();

    public UserDTO login() {
        Scanner scanner = new Scanner(System.in);
        AsciiArt.printSmallFrame("Ingrese Documento y Clave para iniciar sesión");
        // ---
        AsciiArt.printOptionFrame("Documento");
        String username = scanner.nextLine();
        AsciiArt.printOptionFrameEnd();
        //---
        AsciiArt.printOptionFrame("Clave");
        String password = scanner.nextLine();
        AsciiArt.printOptionFrameEnd();

        try {
            UserDTO user = userService.login(username, password);
            if (user != null) {
                AsciiArt.printSmallFrame("Bienvenido AMADO CLIENTE " + user.getName() + " " + user.getLastName());
                return user;
            } else {
                AsciiArt.printSmallFrame("Documento o clave incorrectos.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al intentar iniciar sesión. Intente nuevamente.");
            return null;
        }
    }
}