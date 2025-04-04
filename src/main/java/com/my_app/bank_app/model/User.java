package com.my_app.bank_app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_users", schema = "s_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "Your phone number cannot be blank")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Phone number must be 11 digits and start with +7")
    @Column(name = "phone_number", length = 15, unique =  true, nullable = false)
    private String phoneNumber;

    @NotBlank (message = "Your first name cannot be blank")
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NotBlank (message = "Your last name cannot be blank")
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotBlank (message = "Your password cannot be blank")
    @Column(name = "password")
    private String password;

    @Past (message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private Set<DebitCard> debitCards;

}