package com.ShoeStore.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        // ===== BIỂU ĐỒ DOANH THU =====
    	List<Map<String, Object>> revenueData = new ArrayList<>();

    	revenueData.add(Map.of(
    	    "month", "T1",
    	    "value", "120tr",
    	    "percent", 60
    	));

    	revenueData.add(Map.of(
    	    "month", "T2",
    	    "value", "150tr",
    	    "percent", 75
    	));

    	revenueData.add(Map.of(
    	    "month", "T3",
    	    "value", "200tr",
    	    "percent", 90
    	));
        // ===== DANH MỤC =====
        List<Map<String, Object>> categoryTrends = List.of(
            Map.of("name", "Nike", "value", 80, "trend", "up", "icon", "bi bi-lightning"),
            Map.of("name", "Adidas", "value", 65, "trend", "neutral", "icon", "bi bi-stars"),
            Map.of("name", "Jordan", "value", 50, "trend", "down", "icon", "bi bi-fire")
        );
        model.addAttribute("categoryTrends", categoryTrends);

        // ===== TOP SẢN PHẨM =====
        List<Map<String, Object>> products = List.of(
            Map.of("name", "Nike Dunk Low", "category", "Nike", "sales", 120, "revenue", "360tr", "image", "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/99486859.png"),
            Map.of("name", "Jordan 1 Low", "category", "Jordan", "sales", 95, "revenue", "310tr", "image", "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/air-jordan.png")
        );
        model.addAttribute("products", products);

        // ===== HOẠT ĐỘNG =====
        List<Map<String, String>> activities = List.of(
            Map.of("text", "<strong>Đơn hàng mới</strong> từ Nguyễn Văn A", "time", "5 phút trước", "type", "success"),
            Map.of("text", "Sản phẩm <strong>Nike Air Force</strong> sắp hết hàng", "time", "30 phút trước", "type", "warning")
        );
        model.addAttribute("activities", activities);

        return "admin/dashboard";
    }
}
