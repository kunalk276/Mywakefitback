package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
