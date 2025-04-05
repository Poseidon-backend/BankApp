package com.my_app.bank_app.controller;

import com.my_app.bank_app.model.DebitCard;
import com.my_app.bank_app.service.DebitCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debit-cards")
public class DebitCardController {

    private final DebitCardService debitCardService;

    public DebitCardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }

    @PostMapping("/generate/user/{userId}")
    public ResponseEntity<DebitCard> createDebitCard(@PathVariable("userId") Long userId) {
        DebitCard newCard = debitCardService.createDebitCard(userId);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

}
