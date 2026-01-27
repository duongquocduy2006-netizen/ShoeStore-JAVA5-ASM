package com.ShoeStore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // Prefix chung cho toàn bộ controller này
public class AdminProductManager {

    // 1. Hiển thị trang danh sách (admin/products.html)
    @GetMapping("/products")
    public String products() {
        return "admin/products"; // Trả về file products.html
    }

    // 2. Hiển thị Form thêm mới
    @GetMapping("/products/create")
    public String showCreateForm() {
        return "admin/product-form"; // Trả về file product-form.html (dùng chung cho thêm/sửa)
    }

    // 3. Hiển thị Form chỉnh sửa (Demo với ID)
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id) {
        // Sau này sẽ gọi Service lấy sản phẩm theo ID để fill vào form
        return "admin/product-form"; 
    }
}