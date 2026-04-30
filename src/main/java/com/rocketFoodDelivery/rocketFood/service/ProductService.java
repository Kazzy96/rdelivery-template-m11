package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Product;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.ProductRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    // Dependency Injection
    private final ProductRepository productRepository;

    // Constructor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Find all products ordered by ID descending
    public List<Product> findAll() {
        return productRepository.findAllByOrderByIdDesc();
    }

    // Find a single product by ID
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    // Create or update a product
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Delete a product by ID
    @Transactional
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}