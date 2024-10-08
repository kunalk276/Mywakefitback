package com.wakefit.ecommerce.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Admin;
import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Category;
import com.wakefit.ecommerce.entity.Feedback;
import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.entity.Payment;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.AddressRepository;
import com.wakefit.ecommerce.repository.AdminRepository;
import com.wakefit.ecommerce.repository.CartRepository;
import com.wakefit.ecommerce.repository.CategoryRepository;
import com.wakefit.ecommerce.repository.FeedbackRepository;
import com.wakefit.ecommerce.repository.OrderRepository;
import com.wakefit.ecommerce.repository.PaymentRepository;
import com.wakefit.ecommerce.repository.ProductRepository;
import com.wakefit.ecommerce.service.AdminService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PaymentRepository paymentRepository;






    @Override
    @Transactional
    public Admin createAdmin(Admin admin) {

        admin.setUserName("Rajan");
        admin.setPassword("Ur@123");


        return adminRepository.save(admin);
    }


    @Override
    public Admin updateAdmin(Long adminId, Admin admin) {
        if (!adminRepository.existsById(adminId)) {
            throw new ResourceNotFoundException("Admin not found with id " + adminId);
        }
        admin.setUserId(adminId);
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new ResourceNotFoundException("Admin not found with id " + adminId);
        }
        adminRepository.deleteById(adminId);
    }

    @Override
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id " + adminId));
    }


    // Product
    @Override
	public Product createProduct(Product product) {
        Long categoryId = product.getCategory().getCategoryId();

        // Fetch the category entity from the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Set the fetched category to the product
        product.setCategory(category);

        // Save the product
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(Long productId, Product product) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }
        product.setProductId(productId);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Category
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
        category.setCategoryId(categoryId);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Order
    @Override
    public Order updateOrder(Long orderId, Order order) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }
        order.setOrderId(orderId);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

    // Cart
    @Override
    public void deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart not found with id " + cartId);
        }
        cartRepository.deleteById(cartId);
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
    }

    // Address
    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address address) {
        if (!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with id " + addressId);
        }
        address.setAddressId(addressId);
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with id " + addressId);
        }
        addressRepository.deleteById(addressId);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + addressId));
    }

    // Feedback
    @Override
    public Feedback updateFeedback(Long feedbackId, Feedback feedback) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new ResourceNotFoundException("Feedback not found with id " + feedbackId);
        }
        feedback.setFeedbackId(feedbackId);
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new ResourceNotFoundException("Feedback not found with id " + feedbackId);
        }
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    public Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + feedbackId));
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Payment
    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + paymentId));
    }




}
