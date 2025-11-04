package com.example.crudapp.service;

import com.example.crudapp.model.Product;
import com.example.crudapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE - ایجاد محصول جدید
    public Product createProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative");
        }

        return productRepository.save(product);
    }

    // READ - دریافت تمام محصولات
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ - دریافت محصول بر اساس ID
    public Optional<Product> getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.findById(id);
    }

    // UPDATE - به‌روزرسانی محصول
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (productDetails.getName() == null || productDetails.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (productDetails.getPrice() == null || productDetails.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be null or negative");
        }

        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            existingProduct.setIsActive(productDetails.getIsActive());
            return productRepository.save(existingProduct);
        });
    }

    // DELETE - حذف محصول
    public boolean deleteProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Additional methods for enhanced functionality

    // دریافت محصولات فعال
    public List<Product> getActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    // جستجو بر اساس نام
    public List<Product> getProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // دریافت محصولات بر اساس محدوده قیمت
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Price range cannot be null");
        }
        if (minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price range cannot contain negative values");
        }
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
