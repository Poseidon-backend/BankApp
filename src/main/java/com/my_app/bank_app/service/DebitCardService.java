package com.my_app.bank_app.service;

import com.my_app.bank_app.model.DebitCard;

import java.util.List;

public interface DebitCardService {

    DebitCard createDebitCard(Long userId, DebitCard debitCard);

    DebitCard getDebitCardById(Long cardId);

    List<DebitCard> getDebitCardsByUserId(Long userId);

    DebitCard updateDebitCard(Long cardId, DebitCard updatedCard);

    void deleteDebitCard(Long cardId);

    DebitCard activateDebitCard(Long cardId);

    DebitCard deactivateDebitCard(Long cardId);

    DebitCard updateBalance(Long cardId, Double amount, boolean isDeposit);

    boolean validateCardNumber(String cardNumber);
}

