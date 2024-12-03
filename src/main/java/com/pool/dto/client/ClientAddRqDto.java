package com.pool.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientAddRqDto {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

}