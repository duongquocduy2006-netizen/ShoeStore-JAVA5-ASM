package com.ShoeStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlashSaleController {
    @GetMapping("/flash-sale")
    public String register() {
        return "client/flash-sale"; 
    }
}