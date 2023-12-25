package com.flipkart.custom_exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(final String message) {
        super(message);
    }
}
