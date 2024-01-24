package com.eshop.cart.repository;

import com.eshop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository <Cart,Long> {

    Cart findByUserId(long userId);
}

