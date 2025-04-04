package com.my_app.bank_app.exception;

public class DebitCardNotFoundException extends RuntimeException {

    public DebitCardNotFoundException(Long id) {
        super("Debit card with ID " + id + " not found");
    }
}
