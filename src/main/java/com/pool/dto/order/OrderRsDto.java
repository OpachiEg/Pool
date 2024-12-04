package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderRsDto {

    @Schema(name = "startAt", description = "Order start", example = "2024-12-03T15:00:00Z")
    private LocalDateTime startAt;

    @Schema(name = "count", description = "Number of orders (available/busy)", example = "2024-12-03T15:00:00Z")
    private long count;

}
