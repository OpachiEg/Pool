package com.pool.repository;

import com.pool.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveClientWhenClientDataIsCorrect() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        clientRepository.save(client);

        assertTrue(clientRepository.existsByEmail("test@test.com"));
    }

    @Test
    public void shouldThrowExceptionWhenSaveClientWithAlreadyUsingEmail() {
        Client client1 = new Client("Ivan","test@test.com","88005553535");
        clientRepository.save(client1);
        Client client2 = new Client("Ivan","test@test.com","88005553535");

        assertThrows(DataIntegrityViolationException.class,() -> clientRepository.save(client2));
    }

    @Test
    public void shouldThrowExceptionWhenSaveClientWithAlreadyUsingPhone() {
        Client client1 = new Client("Ivan","test@test.com","88005553535");
        clientRepository.save(client1);
        Client client2 = new Client("Ivan","test1@test.com","88005553535");

        assertThrows(DataIntegrityViolationException.class,() -> clientRepository.save(client2));
    }

    @Test
    public void shouldReturnNotEmptyOrdersListWhenFindAll() {
        Client client1 = new Client("Ivan","test@test.com","88005553535");
        clientRepository.save(client1);
        Client client2 = new Client("Ivan","test1@test.com","88005553536");
        clientRepository.save(client2);

        List<Client> result = clientRepository.findAll();
        assertEquals(2,result.size());
    }

    @Test
    public void shouldReturnClientWhenFindById() {
        Client client1 = new Client("Ivan","test@test.com","88005553535");
        client1 = clientRepository.save(client1);

        assertEquals(client1,clientRepository.findById(client1.getId()).get());
    }

    @Test
    public void shouldReturnClientReferenceWhenFindReferenceById() {
        Client client1 = new Client("Ivan","test@test.com","88005553535");
        client1 = clientRepository.save(client1);

        assertEquals(client1,clientRepository.getReferenceById(client1.getId()));
    }

}
