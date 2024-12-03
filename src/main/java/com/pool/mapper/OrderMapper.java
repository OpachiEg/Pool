package com.pool.mapper;

import com.pool.dto.order.OrderReserveRsDto;
import com.pool.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id",target = "orderId")
    OrderReserveRsDto map(Order order);

}
