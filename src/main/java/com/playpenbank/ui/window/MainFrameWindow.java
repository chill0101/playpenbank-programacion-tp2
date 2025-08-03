// src/main/java/com/playpenbank/ui/window/MainFrameWindow.java
package com.playpenbank.ui.window;

import com.playpenbank.dto.UserDTO;

import javax.swing.*;
import java.awt.*;

public class MainFrameWindow extends JFrame implements LoginListener {
    private JTabbedPane tabbedPane; // Main tabbed pane for the application
    private LoginWindow loginPanel; // Login panel for user authentication
    private RegisterWindow registerPanel; // Registration panel for new users
    private ClientMenuWindow clientPanel;   // Client menu panel for authenticated users
    private EmployeeMenuWindow employeePanel; // Employee menu panel for authenticated employees
        // All of these have a file named "window" in the same package

    public MainFrameWindow() { // Constructor for the main frame window
        setTitle( "PlayPen Bank ATM" ); // setTitle sets the title of the window
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // setDefaultCloseOperation sets the default close operation for the window
        setSize( 800, 600 );  // setSize sets the size of the window
        setLocationRelativeTo( null ); // setLocationRelativeTo centers the window on the screen

        //sorry for the redundancy but it helps me to remember what this components are for

        tabbedPane = new JTabbedPane(); // Tabbed pane it's mvc soul here, it's the future :o

        // main components initialization
        loginPanel = new LoginWindow(e -> {}, this); // Login panel for user authentication, it implements the LoginListener interface to handle login success
        registerPanel = new RegisterWindow(e -> showLoginTabs()); // Registration panel for new users, it shows the login tabs when registration is complete

        showLoginTabs(); // Show the login and registration at the start

        add(tabbedPane);
    }

    private void showLoginTabs() { // Method to show the login and registration tabs
        tabbedPane.removeAll(); // Remove all existing tabs to reset the view
        tabbedPane.addTab("Ingresar", loginPanel); // Add the login panel as a tab
        tabbedPane.addTab("Registrar clave", registerPanel); // Add the registration panel as a tab
        tabbedPane.setSelectedIndex(0); // Set the selected tab to the login panel
    }

    private void showClientTab(UserDTO user) { // Method to show the client tab after successful login
        tabbedPane.removeAll(); // Remove all existing tabs to reset the view
        clientPanel = new ClientMenuWindow(user, e -> showLoginTabs());
        tabbedPane.addTab( "publicidad: Ya estás registrado en nuestras AFJP 2.0? ", clientPanel );
        tabbedPane.setSelectedComponent(clientPanel); // Set the selected tab to the client panel
    }

    private void showEmployeeTab() { // Method to show the employee tab after successful login
        tabbedPane.removeAll(); // Remove all existing tabs to reset the view
        employeePanel = new EmployeeMenuWindow(e -> showLoginTabs()); // Create a new employee menu window with a logout listener
        tabbedPane.addTab( "Sonría. Lo estamos observando", employeePanel );
        tabbedPane.setSelectedComponent(employeePanel);
    }

    @Override
    public void onLoginSuccess( UserDTO user ) { // show the appropriate tab based on the user type after successful login
        if ( "client".equalsIgnoreCase( user.getUserType()) ) {
            showClientTab(user);
        } else if ("employee".equalsIgnoreCase(user.getUserType()) ) {
            showEmployeeTab();
        }
    }
}