package com.playpenbank.service;

import com.playpenbank.dao.UserDAO;
import com.playpenbank.dao.impl.UserDAOImpl;
import com.playpenbank.dto.UserDTO;

public class UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    //  equals for find dni on db for login
    public UserDTO login(String dni, String password) {
        for (UserDTO user : userDAO.findAll()) {
            if (user.getDni().equals(dni) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    //  equals for find dni on db for register
    public boolean register(UserDTO user) {
        for (UserDTO u : userDAO.findAll()) {
            if (u.getDni().equals(user.getDni())) {
                return false;
            }
        }
        return userDAO.save(user);
    }
}