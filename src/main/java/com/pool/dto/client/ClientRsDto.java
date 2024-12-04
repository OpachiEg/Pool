package com.pool.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRsDto {

    private Long id;
    private String name;
    private String email;
    private String phone;

}
