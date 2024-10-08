package com.wakefit.ecommerce.dto;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Date orderDate;
    private double totalAmount;
    private String paymentStatus;
    private String orderStatus;
    private Address billingAddress; // Include billing address
    private Address shippingAddress; // Include shipping address
    private List<Product> products; // List of products in the order
}