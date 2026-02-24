package com.ShoeStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html
    }
    
    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về file register.html
    }

    // Hàm xử lý khi người dùng bấm nút Đăng Ký
    @PostMapping("/register")
    public String processRegister(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        // 1. Kiểm tra 2 mật khẩu có khớp nhau không
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "register";
        }

        // 2. Kiểm tra xem Email đã tồn tại trong CSDL chưa
        String checkEmailSql = "SELECT COUNT(*) FROM accounts WHERE email = ?";
        Integer count = jdbc.queryForObject(checkEmailSql, Integer.class, email);
        
        if (count != null && count > 0) {
            model.addAttribute("error", "Địa chỉ Email này đã được sử dụng!");
            return "register";
        }

        try {
            // 3. Tạo mã tài khoản tự động (VD: U4592)
            String userCode = "U" + (System.currentTimeMillis() % 10000);

            // 4. Lưu vào Database (mặc định role = USER, status = 1, hạng Đồng = 1)
            String insertSql = "INSERT INTO accounts (user_code, email, password, full_name, role, status, membership_rank_id) " +
                               "VALUES (?, ?, ?, ?, 'USER', 1, 1)";
            jdbc.update(insertSql, userCode, email, password, fullName);

            // 5. Đăng ký thành công -> Báo xanh lá cây
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "register"; // Ở lại trang đăng ký và hiện chữ xanh, hoặc Xếp có thể đổi thành return "login";

        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra trong quá trình đăng ký: " + e.getMessage());
            return "register";
        }
    }
}