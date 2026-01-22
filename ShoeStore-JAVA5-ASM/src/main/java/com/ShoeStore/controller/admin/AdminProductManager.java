package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminProductManager {
	@GetMapping("/admin/products")
	public String products() {
		return "admin/products";
	}

}
