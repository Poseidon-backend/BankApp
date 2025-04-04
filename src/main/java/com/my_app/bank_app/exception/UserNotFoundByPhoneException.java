package com.my_app.bank_app.exception;

public class UserNotFoundByPhoneException extends RuntimeException {

    public UserNotFoundByPhoneException(String phoneNumber) {
        super("User with phone number " + phoneNumber + " not found");
    }
}
