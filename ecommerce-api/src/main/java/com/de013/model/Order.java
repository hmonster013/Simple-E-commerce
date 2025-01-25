package com.de013.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.de013.dto.OrderRequest;
import com.de013.dto.OrderVO;
import com.de013.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

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

    private BigDecimal totalAmount;

    @Size(max = 50)
    private String status;

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
        
        return orderVO;
    }
}
