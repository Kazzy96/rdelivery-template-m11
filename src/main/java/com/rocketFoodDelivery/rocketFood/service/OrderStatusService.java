package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.OrderStatus;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.OrderStatusRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusService {
    // Dependency Injection
    private final OrderStatusRepository orderStatusRepository;

    // Constructor
    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    // Find all order statuses ordered by ID descending
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAllByOrderByIdDesc();
    }

    // Find a single order status by ID
    public Optional<OrderStatus> findById(int id) {
        return orderStatusRepository.findById(id);
    }

    // Create or update an order status
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    // Delete an order status by ID
    @Transactional
    public void deleteById(int id) {
        orderStatusRepository.deleteById(id);
    }
}