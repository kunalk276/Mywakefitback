// FeedbackServiceImpl.java

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        logger.info("Adding feedback: {}", feedbackDTO);
        Feedback feedback = convertToEntity(feedbackDTO);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        logger.info("Feedback added with ID: {}", savedFeedback.getFeedbackId());
        return convertToDTO(savedFeedback);
    }

    @Override
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        logger.info("Fetching feedback with ID: {}", feedbackId);
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
        return convertToDTO(feedback);
    }

    @Override
    @Transactional
    public FeedbackDTO updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) {
        logger.info("Updating feedback with ID: {}", feedbackId);
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
        
        existingFeedback.setRating(feedbackDTO.getRating());
        existingFeedback.setComment(feedbackDTO.getComment());
        existingFeedback.setFeedbackDate(feedbackDTO.getFeedbackDate());

        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        logger.info("Feedback updated with ID: {}", updatedFeedback.getFeedbackId());
        return convertToDTO(updatedFeedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        logger.info("Deleting feedback with ID: {}", feedbackId);
        if (feedbackRepository.existsById(feedbackId)) {
            feedbackRepository.deleteById(feedbackId);
            logger.info("Feedback deleted with ID: {}", feedbackId);
        } else {
            throw new ResourceNotFoundException("Feedback not found with id " + feedbackId);
        }
    }

    @Override
    public List<FeedbackDTO> findAll() {
        logger.info("Fetching all feedbacks");
        return feedbackRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> findFeedbacksByProductId(Long productId) {
        logger.info("Fetching feedbacks for product with ID: {}", productId);
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
            feedback.getProduct() != null ? feedback.getProduct().getImages() : null
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
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + feedbackDTO.getUserId()));
            feedback.setUser(user);
        }

        if (feedbackDTO.getProductId() != null) {
            Product product = productRepository.findById(feedbackDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + feedbackDTO.getProductId()));
            feedback.setProduct(product);
        }

        return feedback;
    }
}