package com.pool.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRsDto {

    @Schema(name = "id", description = "Client id", example = "1")
    private Long id;

    @Schema(name = "name", description = "Client name", example = "Ivan")
    private String name;

    @Schema(name = "email", description = "Client email", example = "test@test.com")
    private String email;

    @Schema(name = "phone", description = "Client phone", example = "8005553535")
    private String phone;

}
