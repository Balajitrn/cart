package com.eshop.cart.service;

import com.eshop.cart.dto.CartDTO;
import com.eshop.cart.dto.CartItemDTO;
import com.eshop.cart.entity.*;
import com.eshop.cart.repository.CartItemRepository;
import com.eshop.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    /**
     *
     * @param cartItemDto
     * @param userId
     * @return
     */
    @Transactional
    public CartDTO addItemToCart(CartItemDTO cartItemDto, Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setQuantity(cartItemDto.getQuantity());


        if (cart != null) {
            List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
            cartItem.setCart(cart);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            cartItems.add(savedCartItem);
            return new CartDTO(cart.getId(), cart.getUserId(), cartItems);
        } else {
           cart = new Cart();

           cart.setUserId(userId);
           Cart savedCart = cartRepository.save(cart);

           cartItem.setCart(cart);
           CartItem savedCartItem =  cartItemRepository.save(cartItem);

            List<CartItem> cartItems = new ArrayList <>();
            cartItems.add(savedCartItem);

            return new CartDTO(savedCart.getId(), savedCart.getUserId(), cartItems);
        }


    }
    @Transactional
    public CartDTO deleteCartItem(Long itemId, Long userId) throws Exception{
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null){
            throw new Exception("No cart exist for userId " + userId );
        }
        Optional <CartItem> cartItem = cartItemRepository.findById(itemId);
        if (!Objects.equals(cart.getId(), cartItem.get().getCart().getId())){
            throw new Exception("The cartItem does not exist in the user's cart with this userId " + userId );
        }
        cartItemRepository.deleteById(itemId);
        return new CartDTO();
    }

}
