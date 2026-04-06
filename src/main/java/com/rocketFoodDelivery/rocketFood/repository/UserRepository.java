package com.rocketFoodDelivery.rocketFood.repository;

import com.rocketFoodDelivery.rocketFood.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Spring Data JPA automatically provides the implementation.
    // save() - findAll() - findById() - deleteById()

    // todo: If needed, add any additional custom query methods

    // Get all users ordered by ID in descending order
    List<User> findAllByOrderByIdDesc();

    // Find user by email
    Optional<User> findByEmail(String email);
}
