//package com.wakefit.ecommerce.entity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class PasswordGenerator {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void generatePassword() {
//        String rawPassword = "Admin@123";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        System.out.println("Hashed Password for 'Admin@123': " + encodedPassword);
//    }
//}
