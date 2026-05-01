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
import com.rocketFoodDelivery.rocketFood.models.Customer;
import com.rocketFoodDelivery.rocketFood.models.User;

// Project services
import com.rocketFoodDelivery.rocketFood.service.AddressService;
import com.rocketFoodDelivery.rocketFood.service.CustomerService;
import com.rocketFoodDelivery.rocketFood.service.UserService;

@Controller
@RequestMapping("/backoffice/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    // READ - List all customers
    @GetMapping
    public String listCustomers(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Customer> customers = customerService.findAll();
            model.addAttribute("customers", customers);
            return "customer/customerList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading customers: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Customer customer = new Customer();
        customer.setUser(new User());
        customer.setAddress(new Address());
        model.addAttribute("customer", customer);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "customer/customerForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
                                 Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "customer/customerForm";
        }
        userService.findById(customer.getUser().getId()).ifPresent(customer::setUser);
        addressService.findById(customer.getAddress().getId()).ifPresent(customer::setAddress);
        try {
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save customer: user already linked to another customer.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving customer: " + e.getMessage());
        }
        return "redirect:/backoffice/customers";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer not found.");
            return "redirect:/backoffice/customers";
        }
        model.addAttribute("customer", customer.get());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "customer/customerForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable int id, @Valid @ModelAttribute("customer") Customer customer,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            customer.setId(id);
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "customer/customerForm";
        }
        customer.setId(id);
        userService.findById(customer.getUser().getId()).ifPresent(customer::setUser);
        addressService.findById(customer.getAddress().getId()).ifPresent(customer::setAddress);
        try {
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update customer: user already linked to another customer.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating customer: " + e.getMessage());
        }
        return "redirect:/backoffice/customers";
    }

    // DELETE - Delete specific customer
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete customer: it is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/backoffice/customers";
    }
}
