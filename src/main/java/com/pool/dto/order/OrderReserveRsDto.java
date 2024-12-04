package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReserveRsDto {

    @Schema(name = "orderId", description = "Order id", example = "1")
    private Long orderId;

}
