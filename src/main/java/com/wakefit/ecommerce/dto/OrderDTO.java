package com.wakefit.ecommerce.dto;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.entity.User;
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
    private User user;
    private Date orderDate;
    private double totalAmount;
    private String paymentStatus;
    private String orderStatus;
    private Address billingAddress;
    private Cart cart;
    private List<Product> products;
    private List<PaymentDTO> payments;
}
