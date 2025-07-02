package com.playpenbank.exception;

/**
 * Custom exception class for handling database-related errors.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}