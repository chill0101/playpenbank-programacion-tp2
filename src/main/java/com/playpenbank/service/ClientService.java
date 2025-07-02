package com.playpenbank.service;

import com.playpenbank.dao.UserDAO;
import com.playpenbank.dao.impl.UserDAOImpl;
import com.playpenbank.dao.AccountDAO;
import com.playpenbank.dao.impl.AccountDAOImpl;
import com.playpenbank.dto.UserDTO;
import com.playpenbank.dto.AccountDTO;
import com.playpenbank.exception.UserAlreadyTakenException;

public class ClientService {
    private final UserDAO userDAO = new UserDAOImpl();
    private final AccountDAO accountDAO = new AccountDAOImpl();

    public boolean registerClient(UserDTO user) throws UserAlreadyTakenException {
        // Check if the user already exists
        for (UserDTO u : userDAO.findAll()) { // Retrieve all users from the database
            if (u.getDni().equals(user.getDni())) { // Check if the user with the same DNI already exists using equals
                throw new UserAlreadyTakenException("El usuario ya existe."); // Throw a custom exception if the user already exists
            }
        }
        // Save the new user
        boolean userSaved = userDAO.save(user);
        if (!userSaved) return false;

        // Retrieve the saved user to create an account - every new user must have an account
        UserDTO savedUser = null;
        for (UserDTO u : userDAO.findAll()) {
            if (u.getDni().equals(user.getDni())) {
                savedUser = u; // Find the saved user by DNI
                break;
            }
        }
        if (savedUser == null) return false; // If the user was not found, return false

        // Create a new account for the saved user using a random account number - this could be improved with a more robust method with a pattern
        String accountNumber = "111-" + (int)(Math.random() * 9000 + 1000) + savedUser.getId(); // Generate a random account number based on the user id
        AccountDTO account = new AccountDTO(
                0,
                accountNumber,
                0.0,
                savedUser.getId(),
                "active"
        );
        return accountDAO.save(account);
    }
}