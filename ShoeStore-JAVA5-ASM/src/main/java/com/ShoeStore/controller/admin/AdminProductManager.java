package com.ShoeStore.controller.admin;

import com.ShoeStore.model.Product;
import com.ShoeStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductManager {

    @Autowired
    private ProductRepository productRepo;

    // 1. Hiển thị trang danh sách sản phẩm từ DB
    @GetMapping("/products")
    public String products(Model model) {
        List<Product> list = productRepo.findAll();
        model.addAttribute("products", list);
        return "admin/products"; 
    }

    @GetMapping("/products/create")
    public String showCreateForm() {
        return "admin/product-form"; 
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id) {
        return "admin/product-form"; 
    }
}