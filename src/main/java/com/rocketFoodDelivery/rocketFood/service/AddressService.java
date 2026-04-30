package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Address;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.AddressRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    // Dependency Injection
    private final AddressRepository addressRepository;

    // Constructor
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // Find all addresses ordered by ID descending
    public List<Address> findAll() {
        return addressRepository.findAllByOrderByIdDesc();
    }

    // Find a single address by ID
    public Optional<Address> findById(int id) {
        return addressRepository.findById(id);
    }

    // Create or update an address
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    // Delete an address by ID
    @Transactional
    public void deleteById(int id) {
        addressRepository.deleteById(id);
    }
}