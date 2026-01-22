package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCustomerManager {
	
	@GetMapping("/admin/customers")
	public String dashBoard() {
		return "admin/customers";
	}
}
