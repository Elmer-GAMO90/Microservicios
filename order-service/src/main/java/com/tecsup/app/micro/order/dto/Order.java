package com.tecsup.app.micro.order.dto;

import com.tecsup.app.micro.order.client.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private String orderNumber;

    //Para que devuelva el user
    private User createdByUser;

    private List<OrderItem> items;

    private String status;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
