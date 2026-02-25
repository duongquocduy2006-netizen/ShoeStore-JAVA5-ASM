package com.ShoeStore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ShoeStore.model.Account;
import com.ShoeStore.repository.AccountRepository;

@Controller
public class ProfileController {

    @Autowired
    private AccountRepository accountRepo;

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        // Lấy email người đang đăng nhập
        String email = principal.getName();

        Account account = accountRepo.findByEmail(email)
                .orElse(null);

        if (account == null) {
            return "redirect:/login";
        }

        model.addAttribute("account", account);

        return "client/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("account") Account form,
                                Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();

        Account existing = accountRepo.findByEmail(email)
                .orElse(null);

        if (existing != null) {
            existing.setFullName(form.getFullName());
            existing.setPhone(form.getPhone());
            accountRepo.save(existing);
        }

        return "redirect:/profile?success";
    }
}