package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderReserveRsDto {

    @Schema(name = "orderId", description = "Order id", example = "1")
    private Long orderId;

}
