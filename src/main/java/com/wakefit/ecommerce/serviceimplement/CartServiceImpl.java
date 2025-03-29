package com.wakefit.ecommerce.serviceimplement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CartRepository;
import com.wakefit.ecommerce.repository.ProductRepository;
import com.wakefit.ecommerce.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Cart addCart(Cart cart) {
        if (cart.getProducts() == null) {
            cart.setProducts(new ArrayList<>());
        }
        
        if (cart.getUser() == null) {
            throw new IllegalArgumentException("Cart must be associated with a user.");
        }
        
        return cartRepository.save(cart);
    }


    @Override
    public Cart getCartById(Long cartId) {
        
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID " + cartId));
    }

    @Override
    public Cart updateCart(Long cartId, Cart updatedCart) {
        Cart existingCart = getCartById(cartId);

        if (updatedCart.getProducts() != null) {
            List<Product> currentProducts = existingCart.getProducts();
            List<Product> newProducts = updatedCart.getProducts();

            // Add new products only if they are not already in the cart
            for (Product product : newProducts) {
                // Check if the product is not already in the cart's product list
                if (!currentProducts.contains(product)) {
                    currentProducts.add(product);  // Add the new product
                }
            }

            // Ensure the products list is updated
            existingCart.setProducts(currentProducts);
        }

        // Save and return the updated cart
        return cartRepository.save(existingCart);
    }



    @Override
    public void deleteCart(Long cartId) {
       
        Cart cartToDelete = getCartById(cartId);
       
        cartRepository.delete(cartToDelete);
    }
    
    
    @Override
    public void updateStockQuantities(List<Product> products) {
        for (Product product : products) {
            Product existingProduct = productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + product.getProductId()));

            if (product.getStockQuantity() > existingProduct.getStockQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product ID " + product.getProductId());
            }

            existingProduct.setStockQuantity(existingProduct.getStockQuantity() - product.getStockQuantity());
            productRepository.save(existingProduct);
        }
    }

    @Override
    public Cart addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + productId));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartsByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUserUserId(userId);
        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("No carts found for user ID " + userId);
        }
        return carts;
    }
	
}