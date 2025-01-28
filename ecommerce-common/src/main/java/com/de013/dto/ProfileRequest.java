package com.de013.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
}
