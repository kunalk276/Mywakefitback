package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
