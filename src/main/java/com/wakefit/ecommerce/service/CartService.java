package com.wakefit.ecommerce.service;

import com.wakefit.ecommerce.entity.Cart;

public interface CartService {

    Cart addCart(Cart cart);

    Cart getCartById(Long cartId);

    Cart updateCart(Long cartId, Cart cart);

    void deleteCart(Long cartId);
}
