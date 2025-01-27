package com.de013.model;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.de013.dto.RoleRequest;
import com.de013.dto.RoleVO;
import com.de013.utils.Utils;
import com.de013.utils.JConstants.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(RoleRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public RoleVO getVO() {
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(this, roleVO);

        return roleVO;
    }
}
