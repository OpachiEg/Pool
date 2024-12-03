package com.pool.controller.timetable;

import com.pool.dto.order.OrderCancelRqDto;
import com.pool.dto.order.OrderReserveRqDto;
import com.pool.dto.order.OrderReserveRsDto;
import com.pool.dto.order.OrderRsDto;
import com.pool.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/pool/timetable")
public class TimetableRestController {

    private final OrderService orderService;

    @GetMapping("/all")
    public List<OrderRsDto> findAll(@RequestParam LocalDate date,@RequestParam(required = false,defaultValue = "false") boolean available) {
        return orderService.getAll(date,available);
    }

    @PostMapping("/reserve")
    public OrderReserveRsDto makeReservation(@Valid @RequestBody OrderReserveRqDto dto) {
        return orderService.reserve(dto);
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody @Valid OrderCancelRqDto dto) {
        orderService.cancelOrder(dto);
    }

}
