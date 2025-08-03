package com.playpenbank.ui.window;

import com.playpenbank.dto.UserDTO;

public interface LoginListener {
    void onLoginSuccess( UserDTO user );
}

// This interface defines a method to handle successful login events. It has a single method `onLoginSuccess` that takes a `UserDTO` object as a parameter.
// Implemented in MainFrameWindow, it is used to update the UI after a user successfully logs in.