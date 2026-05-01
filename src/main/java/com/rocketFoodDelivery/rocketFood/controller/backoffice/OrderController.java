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
import com.rocketFoodDelivery.rocketFood.models.Customer;
import com.rocketFoodDelivery.rocketFood.models.Order;
import com.rocketFoodDelivery.rocketFood.models.OrderStatus;
import com.rocketFoodDelivery.rocketFood.models.Restaurant;

// Project services
import com.rocketFoodDelivery.rocketFood.service.CustomerService;
import com.rocketFoodDelivery.rocketFood.service.OrderService;
import com.rocketFoodDelivery.rocketFood.service.OrderStatusService;
import com.rocketFoodDelivery.rocketFood.service.RestaurantService;

@Controller
@RequestMapping("/backoffice/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderStatusService orderStatusService;

    // READ - List all orders
    @GetMapping
    public String listOrders(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Order> orders = orderService.findAll();
            model.addAttribute("orders", orders);
            return "order/orderList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading orders: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Order order = new Order();
        order.setRestaurant(new Restaurant());
        order.setCustomer(new Customer());
        order.setOrderStatus(new OrderStatus());
        model.addAttribute("order", order);
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("orderStatuses", orderStatusService.findAll());
        return "order/orderForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("orderStatuses", orderStatusService.findAll());
            return "order/orderForm";
        }
        restaurantService.findById(order.getRestaurant().getId()).ifPresent(order::setRestaurant);
        customerService.findById(order.getCustomer().getId()).ifPresent(order::setCustomer);
        orderStatusService.findById(order.getOrderStatus().getId()).ifPresent(order::setOrderStatus);
        try {
            orderService.save(order);
            redirectAttributes.addFlashAttribute("successMessage", "Order created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save order: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving order: " + e.getMessage());
        }
        return "redirect:/backoffice/orders";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Order> order = orderService.findById(id);
        if (order.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Order not found.");
            return "redirect:/backoffice/orders";
        }
        model.addAttribute("order", order.get());
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("orderStatuses", orderStatusService.findAll());
        return "order/orderForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateOrder(@PathVariable int id, @Valid @ModelAttribute("order") Order order,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            order.setId(id);
            model.addAttribute("restaurants", restaurantService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("orderStatuses", orderStatusService.findAll());
            return "order/orderForm";
        }
        order.setId(id);
        restaurantService.findById(order.getRestaurant().getId()).ifPresent(order::setRestaurant);
        customerService.findById(order.getCustomer().getId()).ifPresent(order::setCustomer);
        orderStatusService.findById(order.getOrderStatus().getId()).ifPresent(order::setOrderStatus);
        try {
            orderService.save(order);
            redirectAttributes.addFlashAttribute("successMessage", "Order updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update order: constraint violation.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating order: " + e.getMessage());
        }
        return "redirect:/backoffice/orders";
    }

    // DELETE - Delete specific order
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Order deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete order: it is referenced by product orders.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting order: " + e.getMessage());
        }
        return "redirect:/backoffice/orders";
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