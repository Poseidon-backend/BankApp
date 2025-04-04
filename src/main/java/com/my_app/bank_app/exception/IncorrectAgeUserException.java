package com.my_app.bank_app.exception;

import java.time.LocalDate;

public class IncorrectAgeUserException extends RuntimeException {
    public IncorrectAgeUserException(LocalDate date) {
        super(date + " is an incorrect.\nThe user must be 14 years old.");
    }
}
