package com.elitemart.service;

import com.elitemart.model.Product;
import com.elitemart.model.User;
import com.elitemart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product saveProduct(String name,
                               String description,
                               BigDecimal price,
                               MultipartFile imageFile,
                               User createdBy) throws IOException {
        Product product = new Product();
        product.setName(name.trim());
        product.setDescription(description != null ? description.trim() : null);
        product.setPrice(price);
        product.setCreatedBy(createdBy);

        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageData(imageFile.getBytes());
            product.setImageContentType(imageFile.getContentType());
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public long countProducts() {
        return productRepository.count();
    }
}
