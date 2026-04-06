package com.rocketFoodDelivery.rocketFood.service;

import com.rocketFoodDelivery.rocketFood.models.User;
import com.rocketFoodDelivery.rocketFood.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    // Dependency Injection
    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // todo: Review and complete the User Service class implementation
    // save() - findById() - deleteById()

    // Find all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // todo: If needed, add any additional custom query methods
}