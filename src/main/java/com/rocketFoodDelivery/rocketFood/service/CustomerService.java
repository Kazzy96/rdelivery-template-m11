package com.rocketFoodDelivery.rocketFood.service;

// Project moodels

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.CustomerRepository;

// Java standard library

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    // Dependency Injection
    private final CustomerRepository customerRepository;

    // Constructor
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    // todo: Review and complete the User Service class implementation
    // save() - findById() - findAll()

    // Method to delete a customer by ID
    @Transactional
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

    // todo: If needed, add any additional custom query methods
}