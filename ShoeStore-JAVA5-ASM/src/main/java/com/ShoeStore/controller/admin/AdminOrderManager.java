package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminOrderManager {
	
	@GetMapping("/admin/orders")
	public String dashBoard() {
		return "admin/orders";
	}
}
