package com.rocketFoodDelivery.rocketFood.repository;

import com.rocketFoodDelivery.rocketFood.models.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    // Spring Data JPA automatically provides the implementation.
    // save() - findAll() - findById() - deleteById()

    // Get all restaurants ordered by ID in descending order
    List<Restaurant> findAllByOrderByIdDesc();
}