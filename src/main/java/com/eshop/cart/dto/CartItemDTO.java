package com.eshop.cart.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Long cartId;
}
