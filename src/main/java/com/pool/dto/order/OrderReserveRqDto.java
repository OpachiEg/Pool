package com.pool.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReserveRqDto {

    @NotNull
    private Long clientId;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;

}
