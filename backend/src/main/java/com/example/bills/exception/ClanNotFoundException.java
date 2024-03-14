package com.example.bills.exception;

public class ClanNotFoundException extends RuntimeException {

    public ClanNotFoundException() {
        super("Clan could not be found");
    }

    public ClanNotFoundException(String message) {
        super(message);
    }

    public ClanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
