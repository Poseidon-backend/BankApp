package com.my_app.bank_app.service;

import com.my_app.bank_app.exception.*;
import com.my_app.bank_app.model.DebitCard;
import com.my_app.bank_app.model.User;
import com.my_app.bank_app.repository.DebitCardRepository;
import com.my_app.bank_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebitCardServiceImpl implements DebitCardService {

    private static int MAX_DEBIT_CARDS_PER_USER = 4;

    private final DebitCardRepository debitCardRepository;
    private final UserRepository userRepository;
    private final CardGeneratorService cardGeneratorService;

    public DebitCardServiceImpl(DebitCardRepository debitCardRepository,
                                UserRepository userRepository,
                                CardGeneratorService cardGeneratorService) {
        this.debitCardRepository = debitCardRepository;
        this.userRepository = userRepository;
        this.cardGeneratorService = cardGeneratorService;
    }

    @Override
    @Transactional
    public DebitCard createDebitCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundByIdException(userId));

        if (debitCardRepository.countDebitCardsByUserId(userId) >= MAX_DEBIT_CARDS_PER_USER) {
            throw new MaxLimitDebitCardException(userId);
        }

        DebitCard debitCard = new DebitCard();
        debitCard.setUser(user);
        debitCard.setCardNumber(cardGeneratorService.generateCardNumber());

        String cardholderFullName;
        if (debitCard.getCardholderName() == null || debitCard.getCardholderName().trim().isEmpty()) {
            cardholderFullName = "";
            if (user.getFirstName() != null && !user.getFirstName().trim().isEmpty()) {
                cardholderFullName += user.getFirstName().trim();
            }
            if (user.getLastName() != null && !user.getLastName().trim().isEmpty()) {
                cardholderFullName += (cardholderFullName.isEmpty() ? "" : " ") + user.getLastName().trim();
            }
            if (cardholderFullName.isEmpty()) {
                throw new NoValidCardholderNameException();
            }
            debitCard.setCardholderName(CyrToLatService.toLatin(cardholderFullName).toUpperCase());
        } else {
            cardholderFullName = debitCard.getCardholderName().trim().toUpperCase();
            debitCard.setCardholderName(cardholderFullName);
        }
        debitCard.setCvv(cardGeneratorService.generateCvv());
        debitCard.setCreationDate(LocalDate.now());
        debitCard.setExpirationDate(cardGeneratorService.generateExpirationDate());
        debitCard.setBalance(cardGeneratorService.generateStartBalance());
        debitCard.setStatus(cardGeneratorService.generateStartStatus());
        debitCard.setUser(user);

        return debitCardRepository.save(debitCard);
    }

    @Override
    public DebitCard getDebitCardById(Long cardId) {
        return debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));
    }

    @Override
    public List<DebitCard> getDebitCardsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundByIdException(userId);
        }
        return debitCardRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public DebitCard updateDebitCard(Long cardId, DebitCard updatedCard) {
        DebitCard existingCard = debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));

        if (updatedCard.getCardholderName() != null && !updatedCard.getCardholderName().trim().isEmpty()) {
            existingCard.setCardholderName(updatedCard.getCardholderName().trim().toUpperCase());
        }
        if (updatedCard.getStatus() != null && !updatedCard.getStatus().isEmpty()) {
            existingCard.setStatus(updatedCard.getStatus());
        }
        if (updatedCard.getBalance() != null) {
            existingCard.setBalance(updatedCard.getBalance());
        }

        return debitCardRepository.save(existingCard);
    }

    @Override
    @Transactional
    public void deleteDebitCard(Long cardId) {
        DebitCard card = debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));
        debitCardRepository.delete(card);
    }

    @Override
    @Transactional
    public DebitCard activateDebitCard(Long cardId) {
        DebitCard card = debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));
        card.setStatus("ACTIVE");
        return debitCardRepository.save(card);
    }

    @Override
    @Transactional
    public DebitCard deactivateDebitCard(Long cardId) {
        DebitCard card = debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));
        card.setStatus("INACTIVE");
        return debitCardRepository.save(card);
    }

    @Override
    @Transactional
    public DebitCard updateBalance(Long cardId, Double amount, boolean isDeposit) {
        DebitCard card = debitCardRepository.findById(cardId)
                .orElseThrow(() -> new DebitCardNotFoundException(cardId));

        if (!"ACTIVE".equals(card.getStatus())) {
            throw new InactiveDebitCardException();
        }

        double newBalance = isDeposit ? card.getBalance() + amount : card.getBalance() - amount;
        if (newBalance < 0) {
            throw new InsufficientFundsException();
        }

        card.setBalance(newBalance);
        return debitCardRepository.save(card);
    }

    @Override
    public boolean validateCardNumber(String cardNumber) {
        return cardGeneratorService.validateCardNumber(cardNumber);
    }
}