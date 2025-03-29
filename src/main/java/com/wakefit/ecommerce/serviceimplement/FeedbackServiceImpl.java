package com.wakefit.ecommerce.serviceimplement;

import com.wakefit.ecommerce.dto.FeedbackDTO;
import com.wakefit.ecommerce.entity.Feedback;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.entity.User;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.FeedbackRepository;
import com.wakefit.ecommerce.repository.ProductRepository;
import com.wakefit.ecommerce.repository.UserRepository;
import com.wakefit.ecommerce.service.FeedbackService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        User user = userRepository.findById(feedbackDTO.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + feedbackDTO.getUserId()));
        
        Product product = productRepository.findById(feedbackDTO.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + feedbackDTO.getProductId()));

        Feedback feedback = convertToEntity(feedbackDTO);
        feedback.setUser(user);
        feedback.setProduct(product);

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDTO(savedFeedback);
    }


    @Override
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID " + feedbackId));
        return convertToDTO(feedback);
    }

    @Override
    @Transactional
    public FeedbackDTO updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID " + feedbackId));

        existingFeedback.setRating(feedbackDTO.getRating());
        existingFeedback.setComment(feedbackDTO.getComment());
        existingFeedback.setFeedbackDate(feedbackDTO.getFeedbackDate());

        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        return convertToDTO(updatedFeedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        if (feedbackRepository.existsById(feedbackId)) {
            feedbackRepository.deleteById(feedbackId);
        } else {
            throw new ResourceNotFoundException("Feedback not found with ID " + feedbackId);
        }
    }

    @Override
    public List<FeedbackDTO> findAll() {
        return feedbackRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> findFeedbacksByProductId(Long productId) {
        List<Feedback> feedbacks = feedbackRepository.findByProductProductId(productId);
        return feedbacks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private FeedbackDTO convertToDTO(Feedback feedback) {
        return new FeedbackDTO(
            feedback.getFeedbackId(),
            feedback.getRating(),
            feedback.getComment(),
            feedback.getFeedbackDate(),
            feedback.getUser() != null ? feedback.getUser().getUserId() : null,
            feedback.getProduct() != null ? feedback.getProduct().getProductId() : null,
            feedback.getProduct() != null ? feedback.getProduct().getName() : null,
            feedback.getProduct() != null ? feedback.getProduct().getImages() : null,
            feedback.getUser() != null ? feedback.getUser().getUserName() : null  // Set userName from User entity
        );
    }

    private Feedback convertToEntity(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(feedbackDTO.getFeedbackId());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComment());
        feedback.setFeedbackDate(feedbackDTO.getFeedbackDate());

        if (feedbackDTO.getUserId() != null) {
            User user = userRepository.findById(feedbackDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + feedbackDTO.getUserId()));
            feedback.setUser(user);
        }

        if (feedbackDTO.getProductId() != null) {
            Product product = productRepository.findById(feedbackDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + feedbackDTO.getProductId()));
            feedback.setProduct(product);
        }

        return feedback;
    }
}
