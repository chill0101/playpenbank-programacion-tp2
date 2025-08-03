package com.playpenbank.ui.window;

import com.playpenbank.dto.UserDTO;
import com.playpenbank.exception.UserAlreadyTakenException;
import com.playpenbank.service.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class RegisterWindow extends JPanel {
    private JTextField nameField; // Input field for the user's first name
    private JTextField lastNameField; // Input field for the user's last name
    private JTextField dniField; // Input field for the user's DNI (7-8 digits)
    private JPasswordField passwordField; // Input field for the user's password (4 digits)
    private JLabel messageLabel; // Label to display messages to the user

    /**
     * Constructs a RegisterWindow panel.
     *
     * @param backListener An ActionListener for the "Back" button to navigate to the previous screen.
     *                     - Nombre
     *                     - Apellido
     *                     - DNI (7-8 digits)
     *                     - Clave (4 digits)
     *                     - Message
     *                     - Register button
     *                     - Back button
     */
    public RegisterWindow(ActionListener backListener) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add "Nombre" label and input field
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        // Add "Apellido" label and input field
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        add(lastNameField, gbc);

        // Add "DNI" label and input field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        dniField = new JTextField(15);
        add(dniField, gbc);

        // Add "Clave" label and password field
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Clave (4 dígitos):"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Add message label for feedback
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.BLUE);
        add(messageLabel, gbc);

        // Add buttons for registration and navigation
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton registerButton = new JButton("Registrarse");
        JButton backButton = new JButton("Atrás");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Set action listener for the "Back" button
        backButton.addActionListener(backListener);

        // Set action listener for the "Register" button
        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String dni = dniField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Validate input fields
            if (name.isEmpty() || lastName.isEmpty() || !dni.matches("\\d{7,8}") || !password.matches("\\d{4}")) {
                messageLabel.setText("Por favor, complete todos los campos correctamente. DNI debe tener 7 u 8 dígitos y clave 4 dígitos.");
                return;
            }

            // Create a new UserDTO object
            UserDTO user = new UserDTO(0, dni, password, name, lastName, "client");
            try {
                // Attempt to register the user
                boolean ok = new ClientService().registerClient(user);
                if (ok) {
                    messageLabel.setText("Registro exitoso. AHORA ES PARTE DE NUESTRA FAMILIA PARA SIEMPRE.");
                    clearFields();
                } else {
                    messageLabel.setText("Registro fallido. Intente nuevamente.");
                }
            } catch (UserAlreadyTakenException ex) {
                messageLabel.setText("El usuario existe en sistema.");
            }
        });
    }

    /**
     * Clears all input fields in the registration form.
     */
    private void clearFields() {
        nameField.setText("");
        lastNameField.setText("");
        dniField.setText("");
        passwordField.setText("");
    }
}