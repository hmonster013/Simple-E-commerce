package com.de013.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.de013.model.Order;
import com.de013.model.Review;
import com.de013.model.Role;
import com.de013.model.Wishlist;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String address;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateDate;
    private List<RoleVO> roles;
    // private List<OrderVO> orders;
    private Wishlist wishlist;
    // private List<ReviewVO> reviews;
}
