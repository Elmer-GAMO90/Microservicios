package com.tecsup.app.micro.order.service;

import com.tecsup.app.micro.order.client.Product;
import com.tecsup.app.micro.order.client.ProductClient;
import com.tecsup.app.micro.order.client.User;
import com.tecsup.app.micro.order.client.UserClient;
import com.tecsup.app.micro.order.dto.CreateOrderItemRequest;
import com.tecsup.app.micro.order.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.dto.Order;
import com.tecsup.app.micro.order.dto.OrderItem;
import com.tecsup.app.micro.order.entity.OrderEntity;
import com.tecsup.app.micro.order.entity.OrderItemEntity;
import com.tecsup.app.micro.order.mapper.OrderItemMapper;
import com.tecsup.app.micro.order.mapper.OrderMapper;
import com.tecsup.app.micro.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // 1. Validar el usuario
        User user = userClient.getUserById(request.getUserId());
        if (user == null || user.getId() == null) {
            log.warn("User with ID {} not found or invalid. Creating order with unknown user.", request.getUserId());
            // Depending on requirements, you might throw an exception here
            // Se garantiza resilencia, procedemos a a crear un usuario con el buid
            user = User.builder()
                    .id(request.getUserId())
                    .name("Unknown User")
                    .email("unknown@example.com")
                    .phone("N/A")
                    .address("N/A")
                    .build();
        }

        // 2. Preparar orderItems y calcular el total
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            Product product = productClient.getProductById(itemRequest.getProductId());

            if (product == null || product.getId() == null || product.getPrice() == null) {
                log.warn("Product with ID {} not found or invalid. Skipping item.", itemRequest.getProductId());
                // Garantiza resilencia
                product = Product.builder()
                        .id(itemRequest.getProductId())
                        .name("Unknown Product")
                        .description("N/A")
                        .price(BigDecimal.ZERO)
                        .stock(0)
                        .category("N/A")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
            }

            BigDecimal unitPrice = product.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setCreatedBy(itemRequest.getProductId());
            orderItemEntity.setQuantity(itemRequest.getQuantity());
            orderItemEntity.setUnitPrice(unitPrice);
            orderItemEntity.setSubtotal(subtotal);
            orderItemEntities.add(orderItemEntity);
        }

        // 3. Crear Order Entity
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        orderEntity.setCreatedBy(request.getUserId());
        orderEntity.setStatus("PENDING"); // Initial status
        orderEntity.setTotalAmount(totalAmount);
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUpdatedAt(LocalDateTime.now());

        // Asociar order items con la orden
        for (OrderItemEntity item : orderItemEntities) {
            item.setOrder(orderEntity);
        }
        orderEntity.setItems(orderItemEntities);

        // 4. Save to DB
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        // 5. Map to DTO and return
        Order order = orderMapper.toDomainWithUser(savedOrderEntity, user);
        List<OrderItem> orderItems = savedOrderEntity.getItems().stream()
                .map(itemEntity -> {
                    Product product = productClient.getProductById(itemEntity.getCreatedBy());
                    return orderItemMapper.toDomainWithProduct(itemEntity, product);
                })
                .collect(Collectors.toList());
        order.setItems(orderItems);

        return order;
    }

    public Order getOrderById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);
        if (orderEntity == null) {
            return null;
        }

        User user = userClient.getUserById(orderEntity.getCreatedBy());
        log.info("User : {}", user);

        return orderMapper.toDomainWithUser(orderEntity, user);
    }
}
