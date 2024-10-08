package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {

}
