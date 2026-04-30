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
import com.rocketFoodDelivery.rocketFood.models.Restaurant;

// Project services
import com.rocketFoodDelivery.rocketFood.service.AddressService;
import com.rocketFoodDelivery.rocketFood.service.RestaurantService;
import com.rocketFoodDelivery.rocketFood.service.UserService;

@Controller
@RequestMapping("/backoffice/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    // READ - List all restaurants
    @GetMapping
    public String listRestaurants(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Restaurant> restaurants = restaurantService.findAll();
            model.addAttribute("restaurants", restaurants);
            return "restaurant/restaurantList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading restaurants: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "restaurant/restaurantForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant, BindingResult result,
                                   Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "restaurant/restaurantForm";
        }
        userService.findById(restaurant.getUser().getId()).ifPresent(restaurant::setUser);
        addressService.findById(restaurant.getAddress().getId()).ifPresent(restaurant::setAddress);
        try {
            restaurantService.save(restaurant);
            redirectAttributes.addFlashAttribute("successMessage", "Restaurant created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save restaurant: address already linked to another restaurant.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving restaurant: " + e.getMessage());
        }
        return "redirect:/backoffice/restaurants";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Restaurant> restaurant = restaurantService.findById(id);
        if (restaurant.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Restaurant not found.");
            return "redirect:/backoffice/restaurants";
        }
        model.addAttribute("restaurant", restaurant.get());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "restaurant/restaurantForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateRestaurant(@PathVariable int id, @Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                   BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            restaurant.setId(id);
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "restaurant/restaurantForm";
        }
        restaurant.setId(id);
        userService.findById(restaurant.getUser().getId()).ifPresent(restaurant::setUser);
        addressService.findById(restaurant.getAddress().getId()).ifPresent(restaurant::setAddress);
        try {
            restaurantService.save(restaurant);
            redirectAttributes.addFlashAttribute("successMessage", "Restaurant updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update restaurant: address already linked to another restaurant.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating restaurant: " + e.getMessage());
        }
        return "redirect:/backoffice/restaurants";
    }

    // DELETE - Delete specific restaurant
    @PostMapping("/{id}/delete")
    public String deleteRestaurant(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            restaurantService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Restaurant deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete restaurant: it is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting restaurant: " + e.getMessage());
        }
        return "redirect:/backoffice/restaurants";
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