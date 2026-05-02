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
import com.rocketFoodDelivery.rocketFood.models.OrderStatus;

// Project services
import com.rocketFoodDelivery.rocketFood.service.OrderStatusService;

@Controller
@RequestMapping("/backoffice/order-statuses")
public class OrderStatusController {

    @Autowired
    private OrderStatusService orderStatusService;

    // READ - List all order statuses
    @GetMapping
    public String listOrderStatuses(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<OrderStatus> orderStatuses = orderStatusService.findAll();
            model.addAttribute("orderStatuses", orderStatuses);
            return "orderStatus/orderStatusList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading order statuses: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("orderStatus", new OrderStatus());
        return "orderStatus/orderStatusForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createOrderStatus(@Valid @ModelAttribute("orderStatus") OrderStatus orderStatus, BindingResult result,
                                    Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "orderStatus/orderStatusForm";
        }
        try {
            orderStatusService.save(orderStatus);
            redirectAttributes.addFlashAttribute("successMessage", "Order status created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save order status: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving order status: " + e.getMessage());
        }
        return "redirect:/backoffice/order-statuses";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<OrderStatus> orderStatus = orderStatusService.findById(id);
        if (orderStatus.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Order status not found.");
            return "redirect:/backoffice/order-statuses";
        }
        model.addAttribute("orderStatus", orderStatus.get());
        return "orderStatus/orderStatusForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateOrderStatus(@PathVariable int id, @Valid @ModelAttribute("orderStatus") OrderStatus orderStatus,
                                    BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            orderStatus.setId(id);
            return "orderStatus/orderStatusForm";
        }
        orderStatus.setId(id);
        try {
            orderStatusService.save(orderStatus);
            redirectAttributes.addFlashAttribute("successMessage", "Order status updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update order status: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating order status: " + e.getMessage());
        }
        return "redirect:/backoffice/order-statuses";
    }

    // DELETE - Delete specific order status
    @PostMapping("/{id}/delete")
    public String deleteOrderStatus(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            orderStatusService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Order status deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete order status: it is referenced by existing orders.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting order status: " + e.getMessage());
        }
        return "redirect:/backoffice/order-statuses";
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