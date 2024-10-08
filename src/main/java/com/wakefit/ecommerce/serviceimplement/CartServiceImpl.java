package com.wakefit.ecommerce.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CartRepository;
import com.wakefit.ecommerce.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addCart(Cart cart) {
        // Save the new cart to the database
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long cartId) {
        // Retrieve the cart by ID or throw an exception if not found
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID " + cartId));
    }

    @Override
    public Cart updateCart(Long cartId, Cart updatedCart) {
        // Get the existing cart
        Cart existingCart = getCartById(cartId);

        // Update the existing cart's properties
        existingCart.setUser(updatedCart.getUser()); // Update user if necessary
        existingCart.setProducts(updatedCart.getProducts()); // Update products in the cart

        // Save the updated cart back to the database
        return cartRepository.save(existingCart);
    }

    @Override
    public void deleteCart(Long cartId) {
        // Get the cart to delete
        Cart cartToDelete = getCartById(cartId);
        // Delete the cart from the database
        cartRepository.delete(cartToDelete);
    }
}