package com.ShoeStore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ShoeStore.model.Account;
import com.ShoeStore.repository.AccountRepository;

@Controller
public class ProfileController {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/change-password")
    public String viewChangePassword(Principal principal, Model model) {
        if (principal == null)
            return "redirect:/login";

        Account account = accountRepo.findByEmail(principal.getName()).orElse(null);
        model.addAttribute("account", account);
        return "client/change-password";
    }

    @PostMapping("/profile/change-password")
    public String processChangePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Principal principal,
            Model model) {

        if (principal == null)
            return "redirect:/login";

        String email = principal.getName();
        Account account = accountRepo.findByEmail(email).orElse(null);

        if (account == null)
            return "redirect:/login";

        // Cần truyền account lại cho Sidebar hiển thị tên
        model.addAttribute("account", account);

        // 1. Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không chính xác!");
            return "client/change-password";
        }

        // 2. Kiểm tra mật khẩu mới khớp nhau
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Xác nhận mật khẩu mới không khớp!");
            return "client/change-password";
        }

        // 3. Kiểm tra độ dài mật khẩu (tùy chọn)
        if (newPassword.length() < 6) {
            model.addAttribute("error", "Mật khẩu mới phải từ 6 ký tự trở lên!");
            return "client/change-password";
        }

        // 4. Cập nhật mật khẩu
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepo.save(account);

        model.addAttribute("success", "Đổi mật khẩu thành công!");
        return "client/change-password";
    }
}