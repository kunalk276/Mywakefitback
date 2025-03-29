package com.wakefit.ecommerce.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.OrderRepository;
import com.wakefit.ecommerce.repository.ProductRepository;
import com.wakefit.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Order addOrder(Order order) {
        // Check if the products in the order have sufficient stock
        for (Product product : order.getProducts()) {
            Product existingProduct = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + product.getProductId()));

            if (existingProduct.getStockQuantity() < product.getStockQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product id: " + product.getProductId());
            }
        }

        
        for (Product product : order.getProducts()) {
            Product existingProduct = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + product.getProductId()));

            existingProduct.setStockQuantity(existingProduct.getStockQuantity() - product.getStockQuantity());
            productRepository.save(existingProduct);
        }

        return orderRepository.save(order);
    }




    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        existingOrder.setTotalAmount(updatedOrder.getTotalAmount());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
