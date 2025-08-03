package com.playpenbank.ui.window;

import com.playpenbank.service.UserService;
import com.playpenbank.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The LoginWindow class represents a user interface panel for user authentication.
 * It provides input fields for entering credentials and buttons for login and exit actions.
 */
public class LoginWindow extends JPanel {
    private JTextField dniField; // Input field for user's DNI
    private JPasswordField passwordField; // Input field for the user's password
    private JLabel messageLabel; // Label to display feedback messages
    private LoginListener loginListener; // Listener to handle successful login events

    /**
     * LoginWindow panel.
     *
     * @param backListener  An ActionListener for the "Salir" button to exit the application.
     * @param loginListener A LoginListener to handle successful login events.
     *                      - DNI (7-8 digits)
     *                      - Clave (4 digits)
     *                      - Message label
     *                      - Ingresar button
     *                      - Salir button
     *
     */
    public LoginWindow( ActionListener backListener, LoginListener loginListener ) {
        this.loginListener = loginListener;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add "DNI" label and input field
        JLabel dniLabel = new JLabel("DNI:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(dniLabel, gbc);

        dniField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        add(dniField, gbc);

        // Add "Clave" label and password field
        JLabel passLabel = new JLabel("Clave:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(passwordField, gbc);

        // Add message label for feedback
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.BLUE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(messageLabel, gbc);

        // Add buttons for login and exit
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("Ingresar");
        JButton backButton = new JButton("Salir");
        btnPanel.add(loginButton);
        btnPanel.add(backButton);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Set action listener for the "Ingresar" button
        loginButton.addActionListener(e -> {
            String dni = dniField.getText();
            String password = new String(passwordField.getPassword());
            UserService userService = new UserService();
            UserDTO user = userService.login(dni, password);
            if (user != null) {
                messageLabel.setText("Login successful!");
                if ( loginListener != null ) {
                    loginListener.onLoginSuccess(user);
                }
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        });

        // Set action listener for the "Salir" btn
        backButton.addActionListener(e -> System.exit(0));
    }
}