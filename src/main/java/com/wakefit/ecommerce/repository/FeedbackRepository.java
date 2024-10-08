package com.wakefit.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wakefit.ecommerce.entity.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductProductId(Long productId);
}
