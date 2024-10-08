package com.wakefit.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Admin;
import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Category;
import com.wakefit.ecommerce.entity.Feedback;
import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.entity.Payment;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.service.AdminService;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin
    @PostMapping("/Admin/add")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/Admin/update")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/Admin/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

    @GetMapping("/Admin/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }


    // Product
    @PostMapping("/Admin/products")
    public Product createProduct(@RequestBody Product product) {
        return adminService.createProduct(product);
    }

    @PutMapping("/Admin/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return adminService.updateProduct(id, product);
    }

    @DeleteMapping("/Admin/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
    }

    @GetMapping("/Admin/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return adminService.getProductById(id);
    }

    @GetMapping("/Admin/allProducts")
    public List<Product> getAllProducts() {
        return adminService.getAllProducts();
    }

    // Category
    @PostMapping("/Admin/categories")
    public Category createCategory(@RequestBody Category category) {
        return adminService.createCategory(category);
    }

    @PutMapping("/Admin/categories/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return adminService.updateCategory(id, category);
    }

    @DeleteMapping("/Admin/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
    }

    @GetMapping("/Admin/categories/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return adminService.getCategoryById(id);
    }

    @GetMapping("/Admin/categories")
    public List<Category> getAllCategories() {
        return adminService.getAllCategories();
    }

    // Order
    @PutMapping("/Admin/orders/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return adminService.updateOrder(id, order);
    }

    @DeleteMapping("/Admin/orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        adminService.deleteOrder(id);
    }

    @GetMapping("/Admin/orders/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return adminService.getOrderById(id);
    }

    // Cart
    @DeleteMapping("/Admin/carts/{id}")
    public void deleteCart(@PathVariable Long id) {
        adminService.deleteCart(id);
    }

    @GetMapping("/Admin/carts/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return adminService.getCartById(id);
    }

    // Address
    @PostMapping("/Admin/addresses")
    public Address createAddress(@RequestBody Address address) {
        return adminService.createAddress(address);
    }

    @PutMapping("/Admin/addresses/{id}")
    public Address updateAddress(@PathVariable Long id, @RequestBody Address address) {
        return adminService.updateAddress(id, address);
    }

    @DeleteMapping("/Admin/addresses/{id}")
    public void deleteAddress(@PathVariable Long id) {
        adminService.deleteAddress(id);
    }

    @GetMapping("/Admin/addresses/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return adminService.getAddressById(id);
    }

    // Feedback
    @PutMapping("/Admin/feedbacks/{id}")
    public Feedback updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        return adminService.updateFeedback(id, feedback);
    }

    @DeleteMapping("/Admin/feedbacks/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        adminService.deleteFeedback(id);
    }

    @GetMapping("/Admin/feedbacks/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return adminService.getFeedbackById(id);
    }

    @GetMapping("/Admin/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return adminService.getAllFeedbacks();
    }

    // Payment
    @GetMapping("/Admin/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return adminService.getPaymentById(id);
    }
}
