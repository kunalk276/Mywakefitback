package com.wakefit.ecommerce.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

    private Long feedbackId;
    private int rating;
    private String comment;
    private Date feedbackDate;
    private Long userId;
    private Long productId;
    private String name;
    private String images;
}
