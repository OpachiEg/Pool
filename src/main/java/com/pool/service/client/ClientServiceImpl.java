package com.pool.service.client;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientShortInfoRsDto;
import com.pool.dto.client.ClientUpdateRqDto;
import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import com.pool.mapper.ClientMapper;
import com.pool.model.Client;
import com.pool.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientShortInfoRsDto> findAllClients(int page, int size) {
        return clientRepository.findAll(PageRequest.of(page,size))
                .stream()
                .map(clientMapper::mapShortInfo)
                .collect(Collectors.toList());
    }

    @Override
    public ClientRsDto getClientInfoById(Long id) {
        return clientMapper.map(getById(id));
    }

    @Override
    public ClientRsDto createClient(ClientAddRqDto dto) {
        if(clientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("This email already using");
        }

        if(clientRepository.existsByPhone(dto.getPhone())) {
            throw new BadRequestException("This phone already using");
        }

        Client client = clientRepository.save(clientMapper.map(dto));
        return clientMapper.map(client);
    }

    @Override
    public ClientRsDto updateClient(ClientUpdateRqDto dto) {
        Client dbClient = getById(dto.getId());

        if(clientRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("This email already using");
        }

        if(clientRepository.existsByPhone(dto.getPhone())) {
            throw new BadRequestException("This phone already using");
        }

        dbClient.setName(dto.getName());
        dbClient.setPhone(dto.getPhone());
        dbClient.setEmail(dto.getEmail());

        return clientMapper.map(clientRepository.save(dbClient));
    }

    @Override
    public Client getReferenceById(Long id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }


    private Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
    }
}
