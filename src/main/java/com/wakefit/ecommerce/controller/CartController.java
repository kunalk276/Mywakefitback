package com.wakefit.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wakefit.ecommerce.dto.CartDTO; // Import the CartDTO
import com.wakefit.ecommerce.entity.Cart; // Import the Cart entity
import com.wakefit.ecommerce.service.CartService; // Import the CartService
import com.wakefit.ecommerce.service.UserService; // Import the UserService

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody CartDTO cartDTO) {
        Cart cart = new Cart();
        
        cart.setUser(userService.getUserById(cartDTO.getUserId())); 
        cart.setProducts(cartDTO.getProducts()); 

        Cart addedCart = cartService.addCart(cart);
        return ResponseEntity.ok(addedCart);
    }
    
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @RequestBody CartDTO cartDTO) {
        Cart cart = cartService.getCartById(cartId);
        // Update fields from cartDTO to cart
        cart.setUser(userService.getUserById(cartDTO.getUserId())); // Fetch the user by ID
        cart.setProducts(cartDTO.getProducts());

        Cart updatedCart = cartService.updateCart(cartId, cart);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}