package com.ShoeStore.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ShoeStore.model.Category;

@Controller
@RequestMapping("/admin/categories") 
public class AdminCategoryManager {

    List<Category> categories = new ArrayList<>();

    @GetMapping
    public String index(Model model) {
        model.addAttribute("list", categories);
        return "admin/categories"; 
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add-categories"; 
    }

    @PostMapping("/add")
    public String saveAdd(@ModelAttribute("category") Category category, Model model) {
        category.setId(categories.size() + 1); 
        categories.add(category);
        model.addAttribute("message", "Thêm loại giày mới thành công!");
        return "admin/add-categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Category category = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        model.addAttribute("category", category);
        return "admin/edit-categories";
    }

    @PostMapping("/edit/{id}")
    public String saveUpdate(@PathVariable("id") Integer id, @ModelAttribute("category") Category category, Model model) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                c.setName(category.getName());
                c.setSlug(category.getSlug());
                c.setActive(category.isActive());
                break;
            }
        }
        model.addAttribute("message", "Cập nhật thành công!");
        return "admin/edit-categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        categories.removeIf(c -> c.getId().equals(id));
        return "redirect:/admin/categories";
    }
}