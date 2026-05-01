package com.rocketFoodDelivery.rocketFood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/backoffice")
    public String backoffice() {
        return "redirect:/";
    }
}
