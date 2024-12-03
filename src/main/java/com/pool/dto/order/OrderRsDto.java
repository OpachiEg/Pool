package com.pool.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderRsDto {

    private LocalDateTime startAt;
    private long count;

}
