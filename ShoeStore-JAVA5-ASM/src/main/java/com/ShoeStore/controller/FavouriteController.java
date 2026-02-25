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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class FavouriteController {

    @Autowired
    private FavouriteRepository favouriteRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/favourites")
    public String favourites(Model model, Principal principal) {
        if (principal == null) {
            System.out.println("DEBUG: Favourites access denied - Principal is null");
            return "redirect:/login";
        }

        String email = principal.getName();
        System.out.println("DEBUG: Accessing favourites for user: " + email);
        Account account = accountRepo.findByEmail(email).orElse(null);

        if (account != null) {
            System.out.println("DEBUG: Found account for " + email + " with ID: " + account.getId());
            List<Favourite> favourites = favouriteRepo.findByAccount(account);
            System.out.println("DEBUG: Found " + (favourites != null ? favourites.size() : 0) + " favourites");
            if (favourites != null) {
                for (Favourite f : favourites) {
                    System.out.println("DEBUG: - Favourite Product: " + f.getProduct().getProductName());
                }
            }
            model.addAttribute("favourites", favourites);
        } else {
            System.out.println("DEBUG: Account not found for email: " + email);
        }

        return "client/favourites";
    }

    @PostMapping("/favourites/add/{productId}")
    @ResponseBody
    public String addFavourite(@PathVariable Integer productId, Principal principal) {
        if (principal == null)
            return "error_auth";

        Account account = accountRepo.findByEmail(principal.getName()).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);

        if (account == null) {
            System.out.println("DEBUG: Failed to add favourite - Account not found for email: " + principal.getName());
            return "error_account";
        }
        if (product == null) {
            System.out.println("DEBUG: Failed to add favourite - Product not found for ID: " + productId);
            return "error_product";
        }

        System.out.println(
                "DEBUG: Adding favourite - Account ID: " + account.getId() + ", Product: " + product.getProductName());
        if (!favouriteRepo.existsByAccountAndProduct(account, product)) {
            Favourite fav = new Favourite();
            fav.setAccount(account);
            fav.setProduct(product);
            favouriteRepo.save(fav);
            System.out.println("DEBUG: Favourite saved successfully");
            return "success";
        } else {
            System.out.println("DEBUG: Favourite already exists");
            return "exists";
        }
    }

    @PostMapping("/favourites/remove/{productId}")
    public String removeFavourite(@PathVariable Integer productId, Principal principal) {
        if (principal == null)
            return "redirect:/login";

        Account account = accountRepo.findByEmail(principal.getName()).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);

        if (account != null && product != null) {
            Optional<Favourite> fav = favouriteRepo.findByAccountAndProduct(account, product);
            fav.ifPresent(favourite -> favouriteRepo.delete(favourite));
        }

        return "redirect:/favourites";
    }

    @PostMapping("/api/favourites/remove/{productId}")
    @ResponseBody
    public String removeFavouriteAjax(@PathVariable Integer productId, Principal principal) {
        if (principal == null)
            return "error_auth";

        Account account = accountRepo.findByEmail(principal.getName()).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);

        if (account != null && product != null) {
            Optional<Favourite> fav = favouriteRepo.findByAccountAndProduct(account, product);
            if (fav.isPresent()) {
                favouriteRepo.delete(fav.get());
                return "success";
            }
        }
        return "error";
    }
}