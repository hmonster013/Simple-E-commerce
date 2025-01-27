package com.de013.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.de013.utils.JConstants.ERole;
import com.de013.utils.JConstants.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterVO implements Serializable {
    Long id = 0L;
    String name = "";
    String status = "";
    ERole role;
    int page = 1;
    int size = 10;
    String sortColumn = "id";
    String sortOrder = Direction.ASC.name();
    // Category
    List<Integer> categories;
    // Order Item
    BigDecimal fromPrice = new BigDecimal(0);
    BigDecimal toPrice = new BigDecimal(1000000000);
    // User
    Long userId = 0L;
    // Order
    Boolean orderHistory = false;
    OrderStatus orderStatus;
    // Product
    Long productId = 0L;
}
