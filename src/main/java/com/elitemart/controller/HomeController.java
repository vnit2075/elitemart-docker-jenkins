package com.elitemart.controller;

import com.elitemart.model.Product;
import com.elitemart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    /* ─── Home page (shows up to 8 featured products) ─── */
    @GetMapping("/")
    public String home(Model model) {
        List<Product> all = productService.getAllProducts();
        model.addAttribute("products",      all.size() > 8 ? all.subList(0, 8) : all);
        model.addAttribute("totalProducts", all.size());
        return "index";
    }

    /* ─── All products page ─── */
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    /* ─── Serve product image bytes ─── */
    @GetMapping("/products/image/{id}")
    public ResponseEntity<byte[]> productImage(@PathVariable Long id) {
        return productService.getProductById(id)
                .filter(Product::hasImage)
                .map(p -> {
                    String contentType = p.getImageContentType() != null
                            ? p.getImageContentType() : "image/jpeg";
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(contentType));
                    headers.setCacheControl(CacheControl.maxAge(java.time.Duration.ofDays(7)));
                    return ResponseEntity.ok().headers(headers).body(p.getImageData());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
