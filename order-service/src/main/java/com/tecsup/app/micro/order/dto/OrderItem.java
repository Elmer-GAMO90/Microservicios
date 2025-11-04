package com.tecsup.app.micro.order.dto;

import com.tecsup.app.micro.order.client.Product;
import com.tecsup.app.micro.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Long id;

    private OrderEntity order;

    //private Long productId;

    //Para que devuelva el producto
    private Product createdByProduct;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
