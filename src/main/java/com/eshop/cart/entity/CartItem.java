package com.eshop.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Long productId;

}
