package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.ProductOrder;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.ProductOrderRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderService {
    // Dependency Injection
    private final ProductOrderRepository productOrderRepository;

    // Constructor
    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    // Find all product orders ordered by ID descending
    public List<ProductOrder> findAll() {
        return productOrderRepository.findAllByOrderByIdDesc();
    }

    // Find a single product order by ID
    public Optional<ProductOrder> findById(int id) {
        return productOrderRepository.findById(id);
    }

    // Create or update a product order
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    // Delete a product order by ID
    @Transactional
    public void deleteById(int id) {
        productOrderRepository.deleteById(id);
    }
}