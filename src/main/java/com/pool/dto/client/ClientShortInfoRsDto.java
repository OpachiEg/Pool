package com.pool.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClientShortInfoRsDto {

    @Schema(name = "id", description = "Client id", example = "1")
    private Long id;

    @Schema(name = "name", description = "Client name", example = "Ivan")
    private String name;

}
