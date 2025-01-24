package com.de013.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Check;
import org.springframework.beans.BeanUtils;

import com.de013.dto.ReviewRequest;
import com.de013.dto.ReviewVO;
import com.de013.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "reviews"
)
@Check(
    constraints = "rating >= 1 and rating <= 5"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Review(ReviewRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public ReviewVO getVO() {
        ReviewVO reviewVO = new ReviewVO();
        BeanUtils.copyProperties(this, reviewVO);

        return reviewVO;
    }
}
