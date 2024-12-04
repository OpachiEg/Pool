package com.pool.controller.timetable;

import com.pool.dto.order.OrderCancelRqDto;
import com.pool.dto.order.OrderReserveRqDto;
import com.pool.dto.order.OrderReserveRsDto;
import com.pool.dto.order.OrderRsDto;
import com.pool.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/pool/timetable")
@Tag(name = "Timetable")
public class TimetableRestController {

    private final OrderService orderService;

    @Operation(summary = "Find all orders on the specified date")
    @GetMapping("/all")
    public List<OrderRsDto> findAll(@RequestParam LocalDate date,@RequestParam(required = false,defaultValue = "false") boolean available) {
        return orderService.getAll(date,available);
    }

    @Operation(summary = "Create reservation")
    @PostMapping("/reserve")
    public OrderReserveRsDto makeReservation(@Valid @RequestBody OrderReserveRqDto dto) {
        return orderService.reserve(dto);
    }

    @Operation(summary = "Cancel reservation")
    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody @Valid OrderCancelRqDto dto) {
        orderService.cancelOrder(dto);
    }

}
