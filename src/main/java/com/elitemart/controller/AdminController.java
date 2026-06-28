package com.elitemart.controller;

import com.elitemart.model.User;
import com.elitemart.repository.UserRepository;
import com.elitemart.service.ProductService;
import com.elitemart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final UserService    userService;
    private final UserRepository userRepository;

    /* ─── Dashboard ─── */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("products",     productService.getAllProducts());
        model.addAttribute("productCount", productService.countProducts());
        model.addAttribute("userCount",    userService.countUsers());
        return "admin/dashboard";
    }

    /* ─── Add Product – form ─── */
    @GetMapping("/products/add")
    public String addProductForm() {
        return "admin/add-product";
    }

    /* ─── Add Product – submit ─── */
    @PostMapping("/products/add")
    public String addProduct(@RequestParam                        String      name,
                             @RequestParam(defaultValue = "")    String      description,
                             @RequestParam                        BigDecimal  price,
                             @RequestParam(value = "image",
                                           required = false)     MultipartFile image,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        try {
            User admin = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new IllegalStateException("Admin user not found"));
            productService.saveProduct(name, description, price, image, admin);
            redirectAttributes.addFlashAttribute("success",
                    "✅ Product \"" + name + "\" added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Failed to add product: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    /* ─── Delete Product ─── */
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("success", "Product deleted successfully.");
        return "redirect:/admin/dashboard";
    }
}
