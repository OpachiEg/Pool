package com.pool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRsDto {

    @Schema(name = "message", example = "Client not found")
    private String message;

}
