package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Restaurant;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.RestaurantRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    // Dependency Injection
    private final RestaurantRepository restaurantRepository;

    // Constructor
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Find all restaurants ordered by ID descending
    public List<Restaurant> findAll() {
        return restaurantRepository.findAllByOrderByIdDesc();
    }

    // Find a single restaurant by ID
    public Optional<Restaurant> findById(int id) {
        return restaurantRepository.findById(id);
    }

    // Create or update a restaurant
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // Delete a restaurant by ID
    @Transactional
    public void deleteById(int id) {
        restaurantRepository.deleteById(id);
    }
}