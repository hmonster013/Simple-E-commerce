package com.de013.dto;

import java.io.Serializable;

import com.de013.utils.JConstants.ERole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest implements Serializable {
    private Integer id;
    private ERole name;
}
