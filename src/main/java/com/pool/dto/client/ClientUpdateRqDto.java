package com.pool.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientUpdateRqDto {

    @Schema(name = "id", description = "Client id", example = "1")
    @NotNull
    @Min(1)
    private Long id;

    @Schema(name = "name", description = "Client name", example = "Ivan")
    @NotBlank
    private String name;

    @Schema(name = "email", description = "Client email", example = "test@test.com")
    @NotBlank
    @Email
    private String email;

    @Schema(name = "phone", description = "Client phone", example = "8005553535")
    @NotBlank
    @Pattern(regexp = "[0-9]+")
    @Size(min = 9,max = 12)
    private String phone;

}
