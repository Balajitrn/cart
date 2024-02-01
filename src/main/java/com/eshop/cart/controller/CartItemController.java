package com.eshop.cart.controller;

import com.eshop.cart.dto.CartDTO;
import com.eshop.cart.dto.CartItemDTO;
import com.eshop.cart.entity.Cart;
import com.eshop.cart.entity.CartItem;
import com.eshop.cart.service.CartItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO) throws NotFoundException{
        try{
            return new ResponseEntity<>(cartItemService.createCart(cartDTO), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDTO cartItemDTO, @PathVariable Long userId) throws Exception {
        try {
            CartItemDTO savedCartItemDTO = cartItemService.addItemToCart(cartItemDTO, userId);
            return ResponseEntity.ok(savedCartItemDTO);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<?> removeItemByUserIdItemId(@PathVariable Long userId,@PathVariable Long itemId){
        try{
            System.out.println("User Id " + userId + "Item id " +itemId) ;
            CartItemDTO deletedItem = cartItemService.removeItemByUserIdItemId(userId,itemId);
            return ResponseEntity.ok(deletedItem);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getAllItemsByUserId(@PathVariable Long userId) throws Exception{
        List<CartItemDTO> list = cartItemService.getAllCartItemsByUserId(userId);
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(cartItemService.getAllCartItemsByUserId(userId),HttpStatus.OK);
    }

}
