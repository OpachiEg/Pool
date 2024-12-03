package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCancelRqDto {

    @Schema(name = "clientId", description = "Client id", example = "1")
    @NotNull
    private Long clientId;

    @Schema(name = "orderId", description = "Order id", example = "1")
    @NotNull
    private Long orderId;

}
