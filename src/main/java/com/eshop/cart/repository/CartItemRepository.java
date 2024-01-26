package com.eshop.cart.repository;

import com.eshop.cart.entity.Cart;
import com.eshop.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository <CartItem,Long> {


    List <CartItem> findAllByCartId(Long cart);

//   public Integer getQuantity();
}

