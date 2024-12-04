package com.pool.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelRqDto {

    @Schema(name = "clientId", description = "Client id", example = "1")
    @NotNull
    private Long clientId;

    @Schema(name = "orderId", description = "Order id", example = "1")
    @NotNull
    private Long orderId;

}
