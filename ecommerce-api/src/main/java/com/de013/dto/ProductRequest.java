package com.de013.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.de013.model.Product;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> imageUrl;
    private int stockQuantity;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createDate;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateDate;

    private List<Integer> categories;
}
