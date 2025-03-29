package com.wakefit.ecommerce.controller;

import com.wakefit.ecommerce.dto.FeedbackDTO;
import com.wakefit.ecommerce.service.FeedbackService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<FeedbackDTO> addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        System.out.println("Received feedback: " + feedbackDTO);
        
        if (feedbackDTO.getUserId() == null || feedbackDTO.getProductId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        FeedbackDTO savedFeedback = feedbackService.addFeedback(feedbackDTO);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }





    @GetMapping("/{feedbackId}")
    public FeedbackDTO getFeedbackById(@PathVariable Long feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @GetMapping
    public List<FeedbackDTO> getFeedbackList() {
        return feedbackService.findAll();
    }

    @PutMapping("/update/{feedbackId}")
    public FeedbackDTO updateFeedback(@PathVariable Long feedbackId, @RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.updateFeedback(feedbackId, feedbackDTO);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public void deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }
}
