package com.tecsup.app.micro.product.dto;

import com.tecsup.app.micro.product.client.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    //private Long createdBy;

    //Para que devuelva el user
    private User createdByUser;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
