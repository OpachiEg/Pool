package com.pool.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAddRqDto {

    @NotBlank
    @Schema(name = "name", description = "Client name", example = "Ivan")
    private String name;

    @NotBlank
    @Email
    @Schema(name = "email", description = "Client email", example = "test@test.com")
    private String email;

    @Schema(name = "phone", description = "Client phone", example = "8005553535")
    @NotBlank
    @Pattern(regexp = "[0-9]+")
    @Size(min = 9,max=12)
    private String phone;

}
