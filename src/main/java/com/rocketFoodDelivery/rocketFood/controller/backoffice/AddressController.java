package com.rocketFoodDelivery.rocketFood.controller.backoffice;

// Java standard library
import java.util.List;
import java.util.Optional;

// Jakarta EE
import jakarta.validation.Valid;

// Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Project models
import com.rocketFoodDelivery.rocketFood.models.Address;

// Project services
import com.rocketFoodDelivery.rocketFood.service.AddressService;

@Controller
@RequestMapping("/backoffice/addresses")
public class AddressController {
    // todo: Implement complete CRUD operations for the Address @Controller class

    @Autowired
    private AddressService addressService;


    // READ - List all addresses
    @GetMapping
    public String listAddresses(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Address> addresses = addressService.findAll();
            model.addAttribute("addresses", addresses);
            return "address/addressList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading addresses: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }


    // CREATE - Show empty form to create new address
    // @GetMapping("/new")

    
    // CREATE - Process form submission to save new address
    // @PostMapping
    

    // UPDATE - Show pre-filled form to edit existing address
    // @GetMapping("/{id}/edit")
    

    // UPDATE - Process form submission to update address
    // @PostMapping("/{id}")
    

    // DELETE - Delete specific address
    // @PostMapping("/{id}/delete")
    
}
