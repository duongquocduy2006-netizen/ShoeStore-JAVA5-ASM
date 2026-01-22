package com.ShoeStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		
		//helo
		return "home";
	}

<<<<<<< HEAD
}
=======
}
//HIHI
//hihi
>>>>>>> 9f563b21e4cf162171542ecf4e959b36fc09a956
