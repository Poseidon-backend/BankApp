package com.my_app.bank_app.exception;

public class NoValidCardholderNameException extends RuntimeException {

    public NoValidCardholderNameException() {
        super("Cardholder name cannot be empty and user has no valid name data");
    }
}
