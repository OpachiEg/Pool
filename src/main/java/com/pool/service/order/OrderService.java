package com.pool.service.order;

import com.pool.dto.order.OrderCancelRqDto;
import com.pool.dto.order.OrderReserveRqDto;
import com.pool.dto.order.OrderReserveRsDto;
import com.pool.dto.order.OrderRsDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderReserveRsDto reserve(OrderReserveRqDto dto);

    List<OrderRsDto> getAll(LocalDate date,boolean available);

    void cancelOrder(OrderCancelRqDto dto);

}
