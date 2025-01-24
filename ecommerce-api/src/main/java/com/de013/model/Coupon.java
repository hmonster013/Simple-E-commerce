package com.de013.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.Check;
import org.springframework.beans.BeanUtils;

import com.de013.dto.CouponRequest;
import com.de013.dto.CouponVO;
import com.de013.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "coupons",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "code"
        })
    }
)
@Check(
    constraints = "discount_percentage >= 0 AND discount_percentage <= 100"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max = 50)
    @Column(nullable = false)
    private String code;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Coupon(CouponRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public CouponVO getVO() {
        CouponVO couponVO = new CouponVO();
        BeanUtils.copyProperties(this, couponVO);
        return couponVO;
    }
}
