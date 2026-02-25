package com.ShoeStore.controller;

import com.ShoeStore.model.Account;
import com.ShoeStore.model.Favourite;
import com.ShoeStore.model.Product;
import com.ShoeStore.repository.AccountRepository;
import com.ShoeStore.repository.FavouriteRepository;
import com.ShoeStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private FavouriteRepository favouriteRepo;

	@Autowired
	private AccountRepository accountRepo;

	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<Product> products = productRepo.findAll();
		List<Product> newArrivals = products.size() > 4 ? products.subList(0, 4) : products;
		model.addAttribute("newArrivals", newArrivals);

		if (principal != null) {
			Account account = accountRepo.findByEmail(principal.getName()).orElse(null);
			if (account != null) {
				List<Favourite> favs = favouriteRepo.findByAccount(account);
				Set<Integer> favProductIds = favs.stream()
						.map(f -> f.getProduct().getId())
						.collect(Collectors.toSet());
				model.addAttribute("favProductIds", favProductIds);
			}
		}

		return "client/home";
	}

}