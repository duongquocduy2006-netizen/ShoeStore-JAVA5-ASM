package com.ShoeStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewArrivalsController {
	@GetMapping("/new-arrivals")
	public String newArrivals() {
		return "client/new-arrivals";
	}
}
