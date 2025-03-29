package com.wakefit.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.wakefit.ecommerce.dto.CartDTO; 
import com.wakefit.ecommerce.entity.Cart; 
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.service.CartService; 
import com.wakefit.ecommerce.service.UserService; 

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{cartId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long cartId, @RequestBody Product product) {
        try {
            Cart cart = cartService.getCartById(cartId);
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
            }

            Optional<Product> existingProduct = cart.getProducts().stream()
                .filter(p -> p.getProductId().equals(product.getProductId()))
                .findFirst();

            if (existingProduct.isPresent()) {
                Product cartProduct = existingProduct.get();
                cartProduct.setStockQuantity(cartProduct.getStockQuantity() + 1);  
            } else {
                product.setStockQuantity(1);
                cart.getProducts().add(product);  
            }

            Cart updatedCart = cartService.updateCart(cartId, cart);
            return ResponseEntity.ok(updatedCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the cart: " + e.getMessage());
        }
    }


    
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cart);
    }


    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable Long cartId, @RequestBody Cart cart) {
        try {
            
            Cart existingCart = cartService.getCartById(cartId);
            if (existingCart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
            }

            
            Cart updatedCart = cartService.updateCart(cartId, cart);
            
            
            return ResponseEntity.ok(updatedCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the cart: " + e.getMessage());
        }
    }




    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
    

}