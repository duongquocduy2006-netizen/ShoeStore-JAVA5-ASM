package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {
	
	@GetMapping("/admin/dashboard")
	public String dashBoard() {
		return "admin/dashboard";
	}
}
