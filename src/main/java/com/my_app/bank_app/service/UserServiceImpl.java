package com.my_app.bank_app.service;

import com.my_app.bank_app.exception.IncorrectAgeUserException;
import com.my_app.bank_app.exception.UserNotFoundByIdException;
import com.my_app.bank_app.exception.UserNotFoundByPhoneException;
import com.my_app.bank_app.model.User;
import com.my_app.bank_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (!isAdult(user.getDateOfBirth())) {
            throw new IncorrectAgeUserException(user.getDateOfBirth());
        }
        return userRepository.save(user);
    }

    @Override
    public boolean isAdult(LocalDate date) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(date, today);
        return period.getYears() >= 14;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundByPhoneException(phoneNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(user.getDateOfBirth());
        }

        return userRepository.save(existingUser);
    }
}