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
import com.rocketFoodDelivery.rocketFood.models.Product;
import com.rocketFoodDelivery.rocketFood.models.Restaurant;

// Project services
import com.rocketFoodDelivery.rocketFood.service.ProductService;
import com.rocketFoodDelivery.rocketFood.service.RestaurantService;

@Controller
@RequestMapping("/backoffice/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;

    // READ - List all products
    @GetMapping
    public String listProducts(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "product/productList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading products: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Product product = new Product();
        product.setRestaurant(new Restaurant());
        model.addAttribute("product", product);
        model.addAttribute("restaurants", restaurantService.findAll());
        return "product/productForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.findAll());
            return "product/productForm";
        }
        restaurantService.findById(product.getRestaurant().getId()).ifPresent(product::setRestaurant);
        try {
            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save product: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving product: " + e.getMessage());
        }
        return "redirect:/backoffice/products";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
            return "redirect:/backoffice/products";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "product/productForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @Valid @ModelAttribute("product") Product product,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("restaurants", restaurantService.findAll());
            return "product/productForm";
        }
        product.setId(id);
        restaurantService.findById(product.getRestaurant().getId()).ifPresent(product::setRestaurant);
        try {
            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update product: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product: " + e.getMessage());
        }
        return "redirect:/backoffice/products";
    }

    // DELETE - Delete specific product
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete product: it is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/backoffice/products";
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