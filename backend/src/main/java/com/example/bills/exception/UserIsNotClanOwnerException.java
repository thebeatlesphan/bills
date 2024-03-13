package com.example.bills.exception;

public class UserIsNotClanOwnerException extends RuntimeException {

    public UserIsNotClanOwnerException() {
        super("User is not the owner of the specified clan");
    }

    public UserIsNotClanOwnerException(String message) {
        super(message);
    }

    public UserIsNotClanOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}