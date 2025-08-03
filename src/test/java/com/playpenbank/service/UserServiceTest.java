package com.playpenbank.service;

import com.playpenbank.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void login_returnsNullOnInvalidCredentials() {
        UserService userService = new UserService();
        UserDTO user = userService.login("nonexistentDni", "wrongPassword");
        assertNull(user, "El usuario no debería existir con estas credenciales.");
    }

    @Test
    void login_returnsNullOnWrongPassword() {
        UserService userService = new UserService();
        UserDTO user = userService.login("12345678", "wrongpass");
        assertNull(user, "No debe logear con clave incorrecta.");
    }
}