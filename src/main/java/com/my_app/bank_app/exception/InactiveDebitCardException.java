package com.my_app.bank_app.exception;

public class InactiveDebitCardException extends RuntimeException {

    public InactiveDebitCardException() {
        super("Cannot update balance of inactive card.\nActivate it first.");
    }
}
