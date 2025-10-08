package com.tecsup.app.micro.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
