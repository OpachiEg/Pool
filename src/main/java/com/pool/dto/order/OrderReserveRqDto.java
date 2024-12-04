package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReserveRqDto {

    @Schema(name = "clientId", description = "Client id", example = "1")
    @NotNull
    private Long clientId;

    @Schema(name = "startAt", description = "Order start", example = "2024-12-03T15:00:00Z")
    @NotNull
    private LocalDateTime startAt;

    @Schema(name = "endAt", description = "Order end", example = "2024-12-03T16:00:00Z")
    @NotNull
    private LocalDateTime endAt;

}
