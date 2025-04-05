package com.my_app.bank_app.exception;

public class MaxLimitDebitCardException extends RuntimeException {
    public MaxLimitDebitCardException(Long userId) {
        super("User " + userId + " has a max limit debit cards");
    }
}
