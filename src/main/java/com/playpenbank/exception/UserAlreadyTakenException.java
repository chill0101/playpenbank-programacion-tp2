package com.playpenbank.exception;

/**
 * Custom exception class for handling user registration errors.
 */
public class UserAlreadyTakenException extends Exception {
    public UserAlreadyTakenException(String message) {
        super(message);
    }
}