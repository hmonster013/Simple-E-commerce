package com.de013.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.de013.model.Order;
import com.de013.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemVO implements Serializable {
    private Long id;
    private Long orderId;
    private ProductVO product;
    private int quantity;
    private BigDecimal price;
}
