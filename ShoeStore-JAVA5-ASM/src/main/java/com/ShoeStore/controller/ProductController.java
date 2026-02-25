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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private FavouriteRepository favouriteRepo;

    @Autowired
    private AccountRepository accountRepo;

    private void addFavInfo(Model model, Principal principal) {
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
    }

    @GetMapping("/shop")
    public String viewShop(Model model, Principal principal) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        addFavInfo(model, principal);
        return "client/shop";
    }

    @GetMapping("/details")
    public String viewProductDetails(@RequestParam Integer id, Model model, Principal principal) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null)
            return "redirect:/shop";
        model.addAttribute("product", product);
        addFavInfo(model, principal);
        return "client/details";
    }

    @GetMapping("/new-arrivals")
    public String viewNewArrivals(Model model, Principal principal) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        addFavInfo(model, principal);
        return "client/new-arrivals";
    }
}