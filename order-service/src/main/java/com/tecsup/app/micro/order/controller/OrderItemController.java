package com.tecsup.app.micro.order.controller;

import com.tecsup.app.micro.order.dto.OrderItem;
import com.tecsup.app.micro.order.service.OrderItemService;
import com.tecsup.app.micro.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/items")
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        log.info("REST request to get orderItem by id: {}", id);
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        if (orderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderItem);
    }
}
