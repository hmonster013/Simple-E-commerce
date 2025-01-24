package com.de013.dto;

import java.io.Serializable;

import com.de013.model.ERole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleVO implements Serializable {
    private Integer id;
    private ERole name;
}
