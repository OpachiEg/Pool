package com.pool.service;

import com.pool.dto.client.ClientAddRqDto;
import com.pool.dto.client.ClientRsDto;
import com.pool.dto.client.ClientUpdateRqDto;
import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import com.pool.mapper.ClientMapper;
import com.pool.model.Client;
import com.pool.repository.ClientRepository;
import com.pool.service.client.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void shouldThrowExceptionWhenUserCreatingEmailAlreadyUsing() {
        ClientAddRqDto dto = new ClientAddRqDto("Test","test@test.com","88005553535");

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BadRequestException.class,() -> {
            clientService.createClient(dto);
        });
    }

    @Test
    public void shouldThrowExceptionWhenUserCreatingAndPhoneAlreadyUsing() {
        ClientAddRqDto dto = new ClientAddRqDto("Test","test@test.com","88005553535");

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clientRepository.existsByPhone(dto.getPhone())).thenReturn(true);

        assertThrows(BadRequestException.class,() -> {
            clientService.createClient(dto);
        });
    }

    @Test
    public void shouldReturnResponseDtoWhenUserCreatingAndEmailAndPhoneNotUsing() {
        ClientAddRqDto dto = new ClientAddRqDto("Test","test@test.com","88005553535");
        Client client = new Client(dto.getName(),dto.getEmail(),dto.getPhone());
        ClientRsDto clientRsDto = new ClientRsDto(1l,dto.getName(),dto.getEmail(),dto.getPhone());

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clientRepository.existsByPhone(dto.getPhone())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        when(clientMapper.map(dto)).thenReturn(client);
        when(clientMapper.map(client)).thenReturn(clientRsDto);

        assertEquals(clientService.createClient(dto),clientRsDto);
    }

    @Test
    public void shouldThrowExceptionWhenUserUpdateEmailAlreadyUsing() {
        ClientUpdateRqDto dto = new ClientUpdateRqDto(1l,"Test","test@test.com","88005553535");

        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BadRequestException.class,() -> {
            clientService.updateClient(dto);
        });
    }

    @Test
    public void shouldThrowExceptionWhenUserUpdatePhoneAlreadyUsing() {
        ClientUpdateRqDto dto = new ClientUpdateRqDto(1l,"Test","test@test.com","88005553535");

        when(clientRepository.findById(1l)).thenReturn(Optional.of(new Client()));
        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clientRepository.existsByPhone(dto.getPhone())).thenReturn(true);

        assertThrows(BadRequestException.class,() -> {
            clientService.updateClient(dto);
        });
    }

    @Test
    public void shouldReturnResponseDtoWhenUserUpdateAndPhoneAndEmailNotUsing() {
        ClientUpdateRqDto dto = new ClientUpdateRqDto(1l,"Test","test@test.com","88005553535");
        Client client = new Client("Test","test@test.com","88005553535");
        client.setId(1l);
        ClientRsDto clientRsDto = new ClientRsDto(1l,dto.getName(),dto.getEmail(),dto.getPhone());

        when(clientRepository.findById(1l)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clientRepository.existsByPhone(dto.getPhone())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.map(client)).thenReturn(clientRsDto);

        assertEquals(clientService.updateClient(dto),clientRsDto);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenGetClientByIdAndClientNotExists() {
        when(clientRepository.findById(1l)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> clientService.getClientInfoById(1l));
    }

}
