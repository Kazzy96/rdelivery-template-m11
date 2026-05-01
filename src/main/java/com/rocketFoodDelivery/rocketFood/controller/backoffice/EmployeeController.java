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
import com.rocketFoodDelivery.rocketFood.models.Employee;
import com.rocketFoodDelivery.rocketFood.models.User;

// Project services
import com.rocketFoodDelivery.rocketFood.service.AddressService;
import com.rocketFoodDelivery.rocketFood.service.EmployeeService;
import com.rocketFoodDelivery.rocketFood.service.UserService;

@Controller
@RequestMapping("/backoffice/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    // READ - List all employees
    @GetMapping
    public String listEmployees(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Employee> employees = employeeService.findAll();
            model.addAttribute("employees", employees);
            return "employee/employeeList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading employees: " + e.getMessage());
            return "redirect:/backoffice";
        }
    }

    // CREATE - Show empty form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Employee employee = new Employee();
        employee.setUser(new User());
        employee.setAddress(new Address());
        model.addAttribute("employee", employee);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "employee/employeeForm";
    }

    // CREATE - Process form submission
    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
                                 Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "employee/employeeForm";
        }
        userService.findById(employee.getUser().getId()).ifPresent(employee::setUser);
        addressService.findById(employee.getAddress().getId()).ifPresent(employee::setAddress);
        try {
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot save employee: user already linked to another employee.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving employee: " + e.getMessage());
        }
        return "redirect:/backoffice/employees";
    }

    // UPDATE - Show pre-filled form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
            return "redirect:/backoffice/employees";
        }
        model.addAttribute("employee", employee.get());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("addresses", addressService.findAll());
        return "employee/employeeForm";
    }

    // UPDATE - Process form submission
    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable int id, @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            employee.setId(id);
            model.addAttribute("users", userService.findAll());
            model.addAttribute("addresses", addressService.findAll());
            return "employee/employeeForm";
        }
        employee.setId(id);
        userService.findById(employee.getUser().getId()).ifPresent(employee::setUser);
        addressService.findById(employee.getAddress().getId()).ifPresent(employee::setAddress);
        try {
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot update employee: user already linked to another employee.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating employee: " + e.getMessage());
        }
        return "redirect:/backoffice/employees";
    }

    // DELETE - Delete specific employee
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete employee: it is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/backoffice/employees";
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
    // @PostMapping("/{id}/delete") {
