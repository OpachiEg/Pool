package com.pool.controller.client;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientShortInfoRsDto;
import com.pool.dto.client.ClientUpdateRqDto;
import com.pool.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/pool/client")
@Tag(name = "Client")
public class ClientRestController {

    private final ClientService clientService;

    @Operation(summary = "Find all clients", description = "Find all clients with pagination")
    @GetMapping("/all")
    public List<ClientShortInfoRsDto> findAll(@RequestParam(required = false,defaultValue = "0") int page,
                                              @RequestParam(required = false,defaultValue = "20") int size) {
        return clientService.findAllClients(page,size);
    }

    @Operation(summary = "Find client info", description = "Find client full info")
    @GetMapping("/get/{id}")
    public ClientRsDto getClientInfoById(@PathVariable Long id) {
        return clientService.getClientInfoById(id);
    }

    @Operation(summary = "Create client")
    @PostMapping("/add")
    public ClientRsDto save(@Valid @RequestBody ClientAddRqDto dto) {
        return clientService.createClient(dto);
    }

    @Operation(summary = "Update client")
    @PutMapping("/update")
    public ClientRsDto update(@Valid @RequestBody ClientUpdateRqDto dto) {
        return clientService.updateClient(dto);
    }

}
