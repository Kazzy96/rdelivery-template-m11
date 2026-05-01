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
import com.rocketFoodDelivery.rocketFood.models.Order;
import com.rocketFoodDelivery.rocketFood.models.Product;
import com.rocketFoodDelivery.rocketFood.models.ProductOrder;

// Project services
import com.rocketFoodDelivery.rocketFood.service.OrderService;
import com.rocketFoodDelivery.rocketFood.service.ProductOrderService;
import com.rocketFoodDelivery.rocketFood.service.ProductService;

@Controller
@RequestMapping("/backoffice/product-orders")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    // READ - List all product orders
    @GetMapping
    public String listProductOrders(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<ProductOrder> productOrders = productOrderService.findAll();
            model.addAttribute("productOrders", productOrders);
            return "productOrder/productOrderList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading product orders: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(new Product());
        productOrder.setOrder(new Order());
        model.addAttribute("productOrder", productOrder);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orders", orderService.findAll());
        return "productOrder/productOrderForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createProductOrder(@Valid @ModelAttribute("productOrder") ProductOrder productOrder, BindingResult result,
                                     Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("products", productService.findAll());
            model.addAttribute("orders", orderService.findAll());
            return "productOrder/productOrderForm";
        }
        productService.findById(productOrder.getProduct().getId()).ifPresent(productOrder::setProduct);
        orderService.findById(productOrder.getOrder().getId()).ifPresent(productOrder::setOrder);
        try {
            productOrderService.save(productOrder);
            redirectAttributes.addFlashAttribute("successMessage", "Product order created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save product order: product already in this order.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving product order: " + e.getMessage());
        }
        return "redirect:/backoffice/product-orders";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ProductOrder> productOrder = productOrderService.findById(id);
        if (productOrder.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product order not found.");
            return "redirect:/backoffice/product-orders";
        }
        model.addAttribute("productOrder", productOrder.get());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orders", orderService.findAll());
        return "productOrder/productOrderForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateProductOrder(@PathVariable int id, @Valid @ModelAttribute("productOrder") ProductOrder productOrder,
                                     BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            productOrder.setId(id);
            model.addAttribute("products", productService.findAll());
            model.addAttribute("orders", orderService.findAll());
            return "productOrder/productOrderForm";
        }
        productOrder.setId(id);
        productService.findById(productOrder.getProduct().getId()).ifPresent(productOrder::setProduct);
        orderService.findById(productOrder.getOrder().getId()).ifPresent(productOrder::setOrder);
        try {
            productOrderService.save(productOrder);
            redirectAttributes.addFlashAttribute("successMessage", "Product order updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update product order: product already in this order.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating product order: " + e.getMessage());
        }
        return "redirect:/backoffice/product-orders";
    }

    // DELETE - Delete specific product order
    @PostMapping("/{id}/delete")
    public String deleteProductOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            productOrderService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product order deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete product order: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product order: " + e.getMessage());
        }
        return "redirect:/backoffice/product-orders";
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