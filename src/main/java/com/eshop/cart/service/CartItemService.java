package com.eshop.cart.service;

import com.eshop.cart.dto.CartDTO;
import com.eshop.cart.dto.CartItemDTO;
import com.eshop.cart.entity.Cart;
import com.eshop.cart.entity.CartItem;
import com.eshop.cart.repository.CartItemRepository;
import com.eshop.cart.repository.CartRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import com.eshop.cart.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    public CartDTO createCart(CartDTO cartDTO) throws IllegalArgumentException{
        Cart checkCart = cartRepository.findByUserId(cartDTO.getUserId());
        if (checkCart == null) {
            Cart cart = cartDtoToEntity(cartDTO);
            Cart savedCart = cartRepository.save(cart);
            return cartEntityToDto(savedCart);
        }
        else{
            throw new IllegalArgumentException("Cart already exists with cart Id " + checkCart.getId() + " with user Id " + cartDTO.getUserId());
        }

    }

    Cart cartDtoToEntity(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        return cart;
    }

    CartDTO cartEntityToDto(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUserId());
        System.out.println(cartDTO);
        return cartDTO;
    }


    @Transactional
    public CartItemDTO addItemToCart(CartItemDTO cartItemDTO,Long userId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId);

        if(cart != null){
            if (cart.getId().equals(cartItemDTO.getCartId())) {
               // System.out.println("inside if else");
                CartItem cartItem = dtoToEntity(cartItemDTO);
                //System.out.println("CART ITEM ENTITY "+cartItem);
                CartItem savedCartItem = cartItemRepository.save(cartItem);
               // System.out.println("SAVED CART ITEM ENTITY "+cartItem);
                return entityToDto(savedCartItem);
            }
            else{
                throw new IllegalArgumentException("Cart Id mismatch!! Cart Id passed should match for User with User Id " + cart.getUserId() + " and Cart ID " + cart.getId());
            }
        }
        else{
            throw new IllegalArgumentException("User not found with Id " + userId);
        }

    }

    @Transactional
    public CartItemDTO removeItemByUserIdItemId(Long userId, Long itemId) throws Exception{
        Cart cart = cartRepository.findByUserId(userId);

        if(cart == null) {
            throw new IllegalArgumentException("No cart exist for userId " + userId);
        }

        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(()-> new NotFoundException("Item not found with itemId "+itemId));
        if(!cart.getId().equals(cartItem.getId())){
            throw new Exception("The cartItem does not exist in the user's cart with this userId " + userId );
        }
        cartItemRepository.deleteById(itemId);
        return entityToDto(cartItem);
    }

    @Transactional
    public List<CartItemDTO> getAllCartItemsByUserId(Long userId) throws IllegalArgumentException{
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new IllegalArgumentException("No cart exist for userId " + userId);
        }

        List<CartItem> cartItemsList = cartItemRepository.findAllByCartId(cart.getId());
        return cartItemsList.stream().map(this::entityToDto).collect(Collectors.toList());
    }



    CartItem dtoToEntity(CartItemDTO cartItemDTO) throws Exception {
        System.out.println("inside dto to entity " +cartItemDTO);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setProductId(cartItemDTO.getProductId());


        if(cartItemDTO.getCartId() != null){
            Cart cart = cartRepository.findById(cartItemDTO.getCartId()).orElseThrow(() -> new NotFoundException("Cart not found"));
            cartItem.setCart(cart);
        }
        else{
            throw new IllegalArgumentException("Cart ID is required");
        }
        System.out.println("inside dto to entity "+ cartItem);
        return cartItem;
    }

    CartItemDTO entityToDto(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setCartId(cartItem.getCart()!= null? cartItem.getCart().getId() : null);
        return cartItemDTO;
    }
}
