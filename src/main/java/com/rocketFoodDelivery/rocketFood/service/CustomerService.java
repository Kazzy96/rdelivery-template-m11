package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Customer;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.CustomerRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    // Dependency Injection
    private final CustomerRepository customerRepository;

    // Constructor
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Find all customers ordered by ID descending
    public List<Customer> findAll() {
        return customerRepository.findAllByOrderByIdDesc();
    }

    // Find a single customer by ID
    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    // Create or update a customer
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer by ID
    @Transactional
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}