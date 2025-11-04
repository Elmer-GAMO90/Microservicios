package com.tecsup.app.micro.order.repository;

import com.tecsup.app.micro.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
