package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
