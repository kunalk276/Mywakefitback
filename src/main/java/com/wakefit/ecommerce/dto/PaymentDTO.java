package com.wakefit.ecommerce.dto;

import java.util.Date;

import com.wakefit.ecommerce.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId; 

    
    private Order order;

    
    private Date paymentDate;

   
    private double amount;

    
    private String paymentMethod;

  
    private String paymentStatus; 
}
