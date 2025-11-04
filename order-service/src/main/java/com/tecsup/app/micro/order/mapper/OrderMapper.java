package com.tecsup.app.micro.order.mapper;

import com.tecsup.app.micro.order.client.User;
import com.tecsup.app.micro.order.dto.Order;
import com.tecsup.app.micro.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    /* OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class); */

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order domain);

    List<Order> toDomain(List<OrderEntity> entities);

    //Como implementar un método en una interface, con default
    default Order toDomainWithUser(OrderEntity orderEntity, User user) {
        Order order = toDomain(orderEntity);
        order.setCreatedByUser(user);
        return order;
    }

}
