package com.wakefit.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private String images;
    private int stockQuantity;
    private Long categoryId;
    private String categoryName;
}
