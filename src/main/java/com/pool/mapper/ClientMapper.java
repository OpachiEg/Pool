package com.pool.mapper;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientShortInfoRsDto;
import com.pool.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client map(ClientAddRqDto dto);

    ClientRsDto map(Client client);

    ClientShortInfoRsDto mapShortInfo(Client client);

}
