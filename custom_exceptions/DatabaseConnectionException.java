package com.flipkart.custom_exceptions;

public class DatabaseConnectionException extends RuntimeException{

    public DatabaseConnectionException(final String message) {
        super(message);
    }
}
