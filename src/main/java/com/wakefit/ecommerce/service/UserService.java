package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.entity.User;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long userId);
    User getUserByUserName(String userName);
    List<User> getAllUsers();
    void deleteUser(Long userId);
    User updateUser(Long userId, User user);
    long getUserCount();
	User register(User user);
   
}
