package com.pool.dto.client;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClientUpdateRqDto {

    @NotNull
    @Min(1)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String phone;

}
