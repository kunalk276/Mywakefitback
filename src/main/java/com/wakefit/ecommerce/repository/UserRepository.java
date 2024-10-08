package com.wakefit.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
