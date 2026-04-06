package com.rocketFoodDelivery.rocketFood.repository;

import com.rocketFoodDelivery.rocketFood.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Spring Data JPA automatically provides the implementation.
    // save() - findAll() - findById() - deleteById()

    // todo: If needed, add any additional custom query methods

    // Get all customers ordered by ID in descending order
    List<Customer> findAllByOrderByIdDesc();

    // Find user by email
    Optional<Customer> findByEmail(String email);
}
