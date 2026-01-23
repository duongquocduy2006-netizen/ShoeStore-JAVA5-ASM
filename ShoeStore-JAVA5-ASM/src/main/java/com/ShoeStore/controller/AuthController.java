package com.ShoeStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html
    }
    
    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về file register.html
    }
}