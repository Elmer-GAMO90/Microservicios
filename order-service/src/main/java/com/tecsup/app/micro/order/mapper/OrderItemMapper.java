package com.tecsup.app.micro.order.mapper;

import com.tecsup.app.micro.order.client.Product;
import com.tecsup.app.micro.order.dto.OrderItem;
import com.tecsup.app.micro.order.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    /* OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class); */

    OrderItem toDomain(OrderItemEntity entity);

    OrderItemEntity toEntity(OrderItem domain);

    List<OrderItem> toDomain(List<OrderItemEntity> entities);

    //Como implementar un método en una interface, con default
    default OrderItem toDomainWithProduct(OrderItemEntity orderItemEntity, Product product) {
        OrderItem orderItem = toDomain(orderItemEntity);
        orderItem.setCreatedByProduct(product);
        return orderItem;
    }
}
