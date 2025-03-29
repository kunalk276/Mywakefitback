package com.wakefit.ecommerce.dto;

import java.util.List;

import com.wakefit.ecommerce.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private Long productId;
    private int quantity;

    
    
}
