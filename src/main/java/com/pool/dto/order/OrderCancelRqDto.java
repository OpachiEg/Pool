package com.pool.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelRqDto {

    @NotNull
    private Long clientId;
    @NotNull
    private Long orderId;

}
