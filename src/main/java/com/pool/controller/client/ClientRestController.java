package com.pool.controller.client;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientShortInfoRsDto;
import com.pool.dto.client.ClientUpdateRqDto;
import com.pool.service.client.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/pool/client")
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping("/all")
    public List<ClientShortInfoRsDto> findAll(@RequestParam(required = false,defaultValue = "0") int page,
                                              @RequestParam(required = false,defaultValue = "20") int size) {
        return clientService.findAllClients(page,size);
    }

    @GetMapping("/get/{id}")
    public ClientRsDto getClientInfoById(@PathVariable Long id) {
        return clientService.getClientInfoById(id);
    }

    @PostMapping("/add")
    public ClientRsDto save(@Valid @RequestBody ClientAddRqDto dto) {
        return clientService.createClient(dto);
    }

    @PutMapping("/update")
    public ClientRsDto update(@Valid @RequestBody ClientUpdateRqDto dto) {
        return clientService.updateClient(dto);
    }

}
