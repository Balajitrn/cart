package com.eshop.cart.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CartItemDTO {
    private long id;
    private long cartId;
    private int quantity;
    private long productId;

}
