package com.de013.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.de013.dto.OrderItemVO;
import com.de013.dto.OrderRequest;
import com.de013.dto.OrderVO;
import com.de013.utils.JConstants.OrderStatus;
import com.de013.utils.JConstants.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderType type;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private String fullName;

    @Size(max = 15)
    private String phoneNumber;
    
    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderItem> orderItems;

    public Order(OrderRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public OrderVO getVO() {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(this, orderVO);
        orderVO.setUser(this.user.getVO());
        if (this.orderItems != null && !this.orderItems.isEmpty()) {
            orderVO.setOrderItems(this.orderItems.stream().map(OrderItem::getVO).toList());
        }

        if (coupon != null) {
            orderVO.setCoupon(this.coupon.getVO());
        }

        return orderVO;
    }
}
