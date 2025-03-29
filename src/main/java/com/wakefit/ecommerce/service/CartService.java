package com.wakefit.ecommerce.service;

import java.util.List;

import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Product;

public interface CartService {

    Cart addCart(Cart cart);

    Cart getCartById(Long cartId);

    Cart updateCart(Long cartId, Cart cart);

    void deleteCart(Long cartId);
    void updateStockQuantities(List<Product> products);
    List<Cart> getCartsByUserId(Long userId);
    Cart addProductToCart(Long cartId, Long productId, int quantity);


}
