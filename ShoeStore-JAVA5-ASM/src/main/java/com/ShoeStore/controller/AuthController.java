package com.ShoeStore.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AuthController {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ----- GET: Hiển thị giao diện -----
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout";
    }

    // --- POST /login is handled by Spring Security ---

    // ----- POST: Xử lý Đăng Ký -----
    @PostMapping("/register")
    public String processRegister(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        // 1. Kiểm tra 2 mật khẩu
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "register";
        }

        // 2. Kiểm tra trùng Email
        String checkEmailSql = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        Integer count = jdbc.queryForObject(checkEmailSql, Integer.class, email);

        if (count != null && count > 0) {
            model.addAttribute("error", "Địa chỉ Email này đã được sử dụng!");
            return "register";
        }

        try {
            // 3. Mã hóa mật khẩu
            String encodedPassword = passwordEncoder.encode(password);

            // 4. Tạo mã ngẫu nhiên
            String userCode = "U" + (System.currentTimeMillis() % 10000);

            // 5. Lưu vào Database
            String insertSql = "INSERT INTO accounts (user_code, email, password, full_name, role, status, membership_rank_id) "
                    +
                    "VALUES (?, ?, ?, ?, 'USER', 1, 1)";
            jdbc.update(insertSql, userCode, email, encodedPassword, fullName);

            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "register";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "register";
        }
    }
}