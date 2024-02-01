package com.eshop.cart.dto;

import com.eshop.cart.entity.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CartDTO {
    private Long id;
    private Long userId;
}
