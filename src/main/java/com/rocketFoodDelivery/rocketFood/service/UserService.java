package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.User;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.UserRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    // Dependency Injection
    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Find all users ordered by ID descending
    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    // Find a single user by ID
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    // Create or update a user
    public User save(User user) {
        return userRepository.save(user);
    }

    // Delete a user by ID
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}