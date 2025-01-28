package com.de013.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.de013.model.User;
import com.de013.utils.JConstants.OrderStatus;
import com.de013.utils.JConstants.OrderType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable{
    private Long id;
    private User user;
    private BigDecimal totalAmount;
    private OrderType type;
    private OrderStatus status;
    private CouponVO coupon;
    private String fullName;
    private String phoneNumber;
    private String shippingAddress;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateDate;

    public OrderRequest(User user, OrderStatus status) {
        this.user = user;
        this.totalAmount = BigDecimal.ZERO;
        this.status = status;
        this.createDate = new Date();
        this.updateDate = new Date();
    }
}
