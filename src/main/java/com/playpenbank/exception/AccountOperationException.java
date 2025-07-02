package com.playpenbank.exception;


/**
 * Custom exception class for handling account operation errors.
 * This exception is thrown when an operation on an account fails.
 */
public class AccountOperationException extends RuntimeException {
    public AccountOperationException(String message) {
        super(message);
    }
}
