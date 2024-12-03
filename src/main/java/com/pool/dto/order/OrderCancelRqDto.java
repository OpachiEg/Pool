package com.pool.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCancelRqDto {

    @NotNull
    private Long clientId;
    @NotNull
    private Long orderId;

}
