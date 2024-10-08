package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.entity.Order;

public interface OrderService {
    Order addOrder(Order order);
    Order getOrderById(Long orderId);
	Order updateOrder(Long orderId, Order updatedOrder);
	void deleteOrder(Long orderId);
	List<Order> getAllOrders();
	List<Order> findAll();

}
