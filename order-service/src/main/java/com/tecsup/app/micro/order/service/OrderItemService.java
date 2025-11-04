package com.tecsup.app.micro.order.service;

import com.tecsup.app.micro.order.client.Product;
import com.tecsup.app.micro.order.client.ProductClient;
import com.tecsup.app.micro.order.client.User;
import com.tecsup.app.micro.order.client.UserClient;
import com.tecsup.app.micro.order.dto.Order;
import com.tecsup.app.micro.order.dto.OrderItem;
import com.tecsup.app.micro.order.entity.OrderEntity;
import com.tecsup.app.micro.order.entity.OrderItemEntity;
import com.tecsup.app.micro.order.mapper.OrderItemMapper;
import com.tecsup.app.micro.order.mapper.OrderMapper;
import com.tecsup.app.micro.order.repository.OrderItemRepository;
import com.tecsup.app.micro.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper mapper;

    private final ProductClient productClient;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper mapper, ProductClient productClient) {
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
        this.productClient = productClient;
    }


    public OrderItem getOrderItemById(Long id) {

        OrderItemEntity  orderItemEntity = orderItemRepository.findById(id).orElse(null);

        Product product = productClient.getProductById(orderItemEntity.getCreatedBy());
        log.info("Product : {}", product);

        return mapper.toDomainWithProduct(orderItemEntity, product);
    }


}
