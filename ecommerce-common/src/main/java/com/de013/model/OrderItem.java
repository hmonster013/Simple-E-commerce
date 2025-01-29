package com.de013.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.de013.dto.OrderItemRequest;
import com.de013.dto.OrderItemVO;
import com.de013.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "order_items"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private int quantity;

    public OrderItem(OrderItemRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    public OrderItem(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.order = orderItem.getOrder();
        this.product = orderItem.getProduct();
        this.quantity = orderItem.getQuantity();
    }

    @JsonIgnore
    public OrderItemVO getVO() {
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(this, orderItemVO);
        orderItemVO.setOrderId(this.order.getId());
        orderItemVO.setProduct(this.product.getVO());
        
        return orderItemVO;
    }
}
