package com.my_app.bank_app.repository;

import com.my_app.bank_app.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

    List<DebitCard> findByUserId(Long userId);
}
