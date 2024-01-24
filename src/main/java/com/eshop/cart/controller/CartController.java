package com.eshop.cart.controller;

import com.eshop.cart.dto.CartDTO;
import com.eshop.cart.dto.CartItemDTO;
import com.eshop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDTO createCart(@RequestBody CartItemDTO cartItemDTO, @PathVariable Long userId) {
        return cartService.addItemToCart(cartItemDTO, userId);
    }
    @DeleteMapping("/{userId}/items/{itemId}")
    @ResponseStatus (HttpStatus.OK)
    public CartDTO deleteItem(@PathVariable Long userId,@PathVariable Long itemId) throws Exception {
        return cartService.deleteCartItem(itemId, userId);
    }


}
