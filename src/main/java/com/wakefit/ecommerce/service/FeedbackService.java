package com.wakefit.ecommerce.service;

import com.wakefit.ecommerce.dto.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO addFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO getFeedbackById(Long feedbackId);
    FeedbackDTO updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO);
    void deleteFeedback(Long feedbackId);
    List<FeedbackDTO> findAll();
    List<FeedbackDTO> findFeedbacksByProductId(Long productId);
}
