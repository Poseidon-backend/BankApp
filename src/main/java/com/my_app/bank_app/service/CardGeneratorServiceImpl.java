package com.my_app.bank_app.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Random;

@Service
public class CardGeneratorServiceImpl implements CardGeneratorService {

    private static final Random RANDOM = new Random();
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int CVV_LENGTH = 3;
    private static final int EXPIRATION_YEARS = 4;

    @Override
    public String generateCardNumber() {
        StringBuilder cardNumber;
        do {
            cardNumber = new StringBuilder();
            for (int i = 0; i < CARD_NUMBER_LENGTH - 1; i++) {
                cardNumber.append(RANDOM.nextInt(10)); // генерит 15 цифр без последней
            }
            cardNumber.append(calculateCheckDigit(cardNumber.toString()));
        } while (!validateCardNumber(cardNumber.toString())); // пока карта не станет валидной генерирую цифры
        return cardNumber.toString();
    }

    @Override
    public String generateCvv() {
        return String.format("%0" + CVV_LENGTH + "d", RANDOM.nextInt(1000));
    }

    @Override
    public String generateExpirationDate() {
        LocalDate expiration = LocalDate.now().plusYears(EXPIRATION_YEARS);
        return String.format("%02d/%02d", expiration.getMonthValue(), expiration.getYear() % 100);
    }

    @Override
    public String generateStartStatus() {
        return "ACTIVE";
    }

    @Override
    public Double generateStartBalance() {
        return 0.0;
    }

    @Override
    public boolean validateCardNumber(String cardNumber) {
        // тут проверка, что номер карты не null и ровно 16 цифр
        if (cardNumber == null || !cardNumber.matches("\\d{" + CARD_NUMBER_LENGTH + "}")) {
            return false;
        }
        int sum = calculateSum(cardNumber, false);
        return sum % 10 == 0;
    }

    private int calculateCheckDigit(String partialCardNumber) {
        int sum = calculateSum(partialCardNumber, true);
        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod; // 16ую цифру получаю
    }

    // этот метод идет с конца номера карты и суммирует цифры и удваивает каждую вторую цифру
    private int calculateSum(String number, boolean alternate) {
        int sum = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }
        return sum;
    }
}