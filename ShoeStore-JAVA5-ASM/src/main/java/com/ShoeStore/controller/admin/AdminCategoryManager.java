package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminCategoryManager {
	
	@GetMapping("/admin/categories")
	public String dashBoard() {
		return "admin/categories";
	}
}
