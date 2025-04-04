package com.my_app.bank_app.service;

public interface CardGeneratorService {

    String generateCardNumber();

    String generateCvv();

    String generateExpirationDate();

    Double generateStartBalance();

    String generateStartStatus();

    boolean validateCardNumber(String cardNumber);

}