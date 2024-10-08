package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
