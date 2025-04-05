package com.my_app.bank_app.repository;

import com.my_app.bank_app.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

    List<DebitCard> findByUserId(Long userId);

    @Query(value = "SELECT COUNT(*) FROM s_users.t_debit_cards WHERE user_id = :userId",
            nativeQuery = true)
    long countDebitCardsByUserId(@Param("userId") Long userId);

}
