package com.my_app.bank_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_debit_cards", schema = "s_users")
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 16, max = 19)
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "cardholder_name", nullable = false)
    private String cardholderName;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$")
    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "balance", nullable = false)
    private Double balance;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}