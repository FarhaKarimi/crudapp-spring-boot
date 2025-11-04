package com.example.crudapp.repository;

import com.example.crudapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // جستجو بر اساس نام (حساس به حروف بزرگ/کوچک نیست)
    List<Product> findByNameContainingIgnoreCase(String name);

    // جستجو بر اساس محدوده قیمت با BigDecimal
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // دریافت محصولات فعال
    List<Product> findByIsActiveTrue();
}
