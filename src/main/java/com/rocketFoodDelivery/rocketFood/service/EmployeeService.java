package com.rocketFoodDelivery.rocketFood.service;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Employee;

// Project repositories
import com.rocketFoodDelivery.rocketFood.repository.EmployeeRepository;

// Spring Framework
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Java standard library
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    // Dependency Injection
    private final EmployeeRepository employeeRepository;

    // Constructor
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Find all employees ordered by ID descending
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByIdDesc();
    }

    // Find a single employee by ID
    public Optional<Employee> findById(int id) {
        return employeeRepository.findById(id);
    }

    // Create or update an employee
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete an employee by ID
    @Transactional
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}