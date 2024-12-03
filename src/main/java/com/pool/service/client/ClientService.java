package com.pool.service.client;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientShortInfoRsDto;
import com.pool.dto.client.ClientUpdateRqDto;
import com.pool.model.Client;

import java.util.List;

public interface ClientService {

    List<ClientShortInfoRsDto> findAllClients(int page, int size);

    ClientRsDto getClientInfoById(Long id);

    ClientRsDto createClient(ClientAddRqDto dto);

    ClientRsDto updateClient(ClientUpdateRqDto dto);

    Client getReferenceById(Long id);

    boolean existsById(Long id);

}
