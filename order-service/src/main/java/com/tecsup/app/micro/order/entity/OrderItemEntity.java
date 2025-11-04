package com.tecsup.app.micro.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")

//Creando la clase OrderItemEntity
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relación muchos a uno (varios items pertenecen a una orden)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;


    @Column(name = "product_id", nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name="unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;


}
