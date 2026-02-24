package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList; 

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String dashBoard(Model model) { 
        
        model.addAttribute("totalRevenue", 0);
        model.addAttribute("newOrdersCount", 0);
        model.addAttribute("lowStockCount", 0);
        model.addAttribute("newCustomersCount", 0);
        model.addAttribute("currentYear", 2024);

       

        return "admin/dashboard";
    }
}