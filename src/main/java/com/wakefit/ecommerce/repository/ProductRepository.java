package com.wakefit.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory_CategoryId(Long categoryId);
}
