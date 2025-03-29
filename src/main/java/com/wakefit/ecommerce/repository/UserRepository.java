package com.wakefit.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findUserByUserNameAndPassword(String userName, String password);
}
