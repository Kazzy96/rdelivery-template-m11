package com.rocketFoodDelivery.rocketFood.repository;

import com.rocketFoodDelivery.rocketFood.models.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    // Spring Data JPA automatically provides the implementation.
    // save() - findAll() - findById() - deleteById()

    // todo: If needed, add any additional custom query methods

    // Get all Addresses ordered by ID in descending order
    List<Address> findAllByOrderByIdDesc();
}