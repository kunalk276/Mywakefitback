package com.wakefit.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wakefit.ecommerce.dto.OrderDTO;
import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.service.OrderService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        // Map fields from orderDTO to order
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setPaymentStatus(orderDTO.getPaymentStatus());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setBillingAddress(orderDTO.getBillingAddress());
        order.setShippingaddress(orderDTO.getShippingAddress());
        order.setProducts(orderDTO.getProducts());

        return orderService.addOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.findAll(); 
    }

}
