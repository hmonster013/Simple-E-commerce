package com.de013.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.de013.model.Order;
import com.de013.model.Product;
import com.de013.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest implements Serializable {
    private Long id;
    private User user;
    private Order order;
    private Product product;
    private int quantity;
    private BigDecimal price;
}
