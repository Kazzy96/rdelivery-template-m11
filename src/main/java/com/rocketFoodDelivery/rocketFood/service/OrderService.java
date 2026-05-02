package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Order;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.OrderRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    // Dependency Injection
    private final OrderRepository orderRepository;

    // Constructor
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Find all orders ordered by ID descending
    public List<Order> findAll() {
        return orderRepository.findAllByOrderByIdDesc();
    }

    // Find a single order by ID
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    // Create or update an order
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    // Delete an order by ID
    @Transactional
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }
}