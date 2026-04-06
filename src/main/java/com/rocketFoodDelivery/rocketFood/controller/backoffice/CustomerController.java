package com.rocketFoodDelivery.rocketFood.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

// Java standard library

// Jakarta EE

// Spring Framework
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Project models

// Project services
import com.rocketFoodDelivery.rocketFood.service.CustomerService;

@Controller
@RequestMapping("/backoffice/customers")
public class CustomerController {
    // todo: Implement complete CRUD operations for the Customer @Controller class

    @Autowired
    private CustomerService customerService;

    // READ - List all addresses
    // @GetMapping


    // CREATE - Show empty form to create new address
    // @GetMapping("/new")

    
    // CREATE - Process form submission to save new address
    // @PostMapping
    

    // UPDATE - Show pre-filled form to edit existing address
    // @GetMapping("/{id}/edit")
    

    // UPDATE - Process form submission to update address
    // @PostMapping("/{id}")
    

    // DELETE - Delete specific address
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete customer with existing orders!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/backoffice/customers";
    }

}
