package com.pool.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderReserveRqDto {

    @NotNull
    private Long clientId;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;

}
