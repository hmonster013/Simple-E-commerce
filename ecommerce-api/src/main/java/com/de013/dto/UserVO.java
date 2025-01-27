package com.de013.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.de013.utils.JConstants.EUserStatus;
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
    private EUserStatus status;

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
}
