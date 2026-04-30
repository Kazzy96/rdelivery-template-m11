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

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("address", new Address());
        return "address/addressForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createAddress(@Valid @ModelAttribute("address") Address address, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "address/addressForm";
        }
        try {
            addressService.save(address);
            redirectAttributes.addFlashAttribute("successMessage", "Address created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save address: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving address: " + e.getMessage());
        }
        return "redirect:/backoffice/addresses";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Address> address = addressService.findById(id);
        if (address.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Address not found.");
            return "redirect:/backoffice/addresses";
        }
        model.addAttribute("address", address.get());
        return "address/addressForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateAddress(@PathVariable int id, @Valid @ModelAttribute("address") Address address,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            address.setId(id);
            return "address/addressForm";
        }
        address.setId(id);
        try {
            addressService.save(address);
            redirectAttributes.addFlashAttribute("successMessage", "Address updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update address: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating address: " + e.getMessage());
        }
        return "redirect:/backoffice/addresses";
    }

    // DELETE - Delete specific address
    @PostMapping("/{id}/delete")
    public String deleteAddress(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            addressService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Address deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete address: it is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting address: " + e.getMessage());
        }
        return "redirect:/backoffice/addresses";
    }
}
