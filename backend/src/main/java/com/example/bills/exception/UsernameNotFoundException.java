package com.example.bills.exception;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException() {
        super("Username not found");
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
