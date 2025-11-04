package com.tecsup.app.micro.order.client;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder

//Esta clase Product se va a comunicar con el microservicio product-service
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;

    //private User createdByUser;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
