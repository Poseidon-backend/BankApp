package com.my_app.bank_app.service;

import com.my_app.bank_app.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User createUser(User user);

    boolean isAdult(LocalDate dateOfBirth);

    User getUserById(Long id);

    User getUserByPhoneNumber(String phoneNumber);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User updateUser(Long id, User user);
}