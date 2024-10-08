package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
