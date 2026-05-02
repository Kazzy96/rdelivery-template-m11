package com.rocketFoodDelivery.rocketFood.repository;

import com.rocketFoodDelivery.rocketFood.models.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Spring Data JPA automatically provides the implementation.
    // save() - findAll() - findById() - deleteById()

    // Get all orders ordered by ID in descending order
    List<Order> findAllByOrderByIdDesc();
}