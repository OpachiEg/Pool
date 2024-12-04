package com.pool.repository;

import com.pool.annotation.DbTest;
import com.pool.config.DbTestContainerConfig;
import com.pool.model.Client;
import com.pool.model.Order;
import com.pool.model.enums.OrderState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DbTest
public class OrderRepositoryTests extends DbTestContainerConfig {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldSaveOrderWhenOrderDataIsCorrect() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        client = clientRepository.save(client);

        Order order = new Order(LocalDateTime.now(),LocalDateTime.now(), OrderState.ACTIVE,client);
        order = orderRepository.save(order);

        assertEquals(order,orderRepository.findById(order.getId()).get());
    }

    @Test
    public void shouldThrowExceptionWhenOrderClientNotSet() {
        Order order = new Order(LocalDateTime.now(),LocalDateTime.now(), OrderState.ACTIVE,null);

        assertThrows(DataIntegrityViolationException.class,() -> orderRepository.save(order));
    }

    @Test
    public void shouldReturnCorrectNumberWhenCountByTimeRange() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        client = clientRepository.save(client);

        LocalDateTime start = LocalDateTime.of(2024,12,4,15,0,0);
        Order order = new Order(start,start.plusHours(1), OrderState.ACTIVE,client);
        orderRepository.save(order);

        assertEquals(1,orderRepository.countByStartAtGreaterThanEqualAndEndAtLessThanEqualAndState(start.minusHours(3),start.plusHours(3),OrderState.ACTIVE));
    }

    @Test
    public void shouldReturnTrueWhenCheckExistsOrderAtThisDate() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        client = clientRepository.save(client);

        LocalDateTime date = LocalDateTime.now();
        Order order = new Order(date,date, OrderState.ACTIVE,client);
        orderRepository.save(order);

        assertTrue(orderRepository.existsOrderAtThisDay(date.toLocalDate(),OrderState.ACTIVE));
    }

    @Test
    public void shouldReturnNotEmptyOrdersListWhenFindOrdersByDate() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        client = clientRepository.save(client);

        LocalDateTime date = LocalDateTime.now();
        Order order = new Order(date,date, OrderState.ACTIVE,client);
        orderRepository.save(order);

        assertEquals(1,orderRepository.findAllByDateAndState(date.toLocalDate(),OrderState.ACTIVE).size());
    }

    @Test
    public void shouldReturnOrderWhenFindByIdAndClientId() {
        Client client = new Client("Ivan","test@test.com","88005553535");
        client = clientRepository.save(client);

        LocalDateTime date = LocalDateTime.now();
        Order order = new Order(date,date, OrderState.ACTIVE,client);
        order = orderRepository.save(order);

        assertEquals(order,orderRepository.findByIdAndClientId(order.getId(),client.getId()).get());
    }

}
