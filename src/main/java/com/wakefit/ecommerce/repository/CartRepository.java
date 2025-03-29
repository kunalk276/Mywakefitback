package com.wakefit.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByUserUserId(Long userId);
}
