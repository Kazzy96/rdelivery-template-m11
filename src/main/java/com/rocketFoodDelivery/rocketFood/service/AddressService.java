package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Address;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.AddressRepository;

// Spring Framework
import org.springframework.stereotype.Service;

// Java standard library
import java.util.List;

@Service
public class AddressService {
    // Dependency Injection
    private final AddressRepository addressRepository;

    // Constructor
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }
    
    // todo: Review and complete the Address Service class implementation
    // save() - findById() - deleteById()
    
    // Method to find all addresses
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
    
    // todo: If needed, add any additional custom query methods
}