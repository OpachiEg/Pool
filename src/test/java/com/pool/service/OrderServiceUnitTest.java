package com.pool.service;

import com.pool.dto.order.OrderCancelRqDto;
import com.pool.dto.order.OrderReserveRqDto;
import com.pool.dto.order.OrderReserveRsDto;
import com.pool.dto.order.OrderRsDto;
import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import com.pool.mapper.OrderMapper;
import com.pool.model.Client;
import com.pool.model.Order;
import com.pool.model.Timetable;
import com.pool.model.enums.OrderState;
import com.pool.repository.OrderRepository;
import com.pool.service.client.ClientService;
import com.pool.service.order.OrderServiceImpl;
import com.pool.service.timetable.TimetableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TimetableService timetableService;
    @Mock
    private ClientService clientService;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndStartDateAfterEndDate() {
        LocalDateTime now = LocalDateTime.now();
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now.plusHours(1),now);

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("Start time should be after end time",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndDurationIsNotDivisibleBy60WithoutRemainder() {
        LocalDateTime now = LocalDateTime.now();
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusMinutes(10));

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("You can only book for 1,2,3, etc. hours",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndDurationIsNot0() {
        LocalDateTime now = LocalDateTime.now();
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now);

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("You can only book for 1,2,3, etc. hours",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndStartTimeContainsMinutes() {
        LocalDateTime now = LocalDateTime.now();
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now.plusMinutes(15),now.plusMinutes(75));

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("Time must be discrete",e.getMessage());
        }
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenReserveAndUserNotExists() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,15,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        when(clientService.existsById(1l)).thenReturn(false);

        try {
            orderService.reserve(dto);
        } catch (NotFoundException e) {
            assertEquals("Client not found",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndUserAlreadyHaveOrderOnThisDay() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,15,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        when(clientService.existsById(1l)).thenReturn(true);
        when(orderRepository.existsOrderAtThisDay(now.toLocalDate(), OrderState.ACTIVE)).thenReturn(true);

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("You can only make 1 entry per day",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndNoTimetableOnThisDay() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,15,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        when(clientService.existsById(1l)).thenReturn(true);
        when(orderRepository.existsOrderAtThisDay(now.toLocalDate(), OrderState.ACTIVE)).thenReturn(null);
        when(timetableService.getByDayOfWeek(now.getDayOfWeek(),dto.getStartAt().toLocalDate()))
                .thenReturn(Optional.empty());

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("Order on this day is not available",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndOrderDatesNotInBusinessHours() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,13,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        when(clientService.existsById(1l)).thenReturn(true);
        when(orderRepository.existsOrderAtThisDay(now.toLocalDate(), OrderState.ACTIVE)).thenReturn(null);
        when(timetableService.getByDayOfWeek(now.getDayOfWeek(),dto.getStartAt().toLocalDate()))
                .thenReturn(Optional.of(new Timetable(
                        LocalTime.of(15,0,0),
                        LocalTime.of(16,0,0),
                        now.getDayOfWeek(),
                        null
                )));

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("You can only make an order during business hours",e.getMessage());
        }
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenReserveAndNoPlacesAtThisTime() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,15,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        when(orderRepository.countByStartAtGreaterThanEqualAndEndAtLessThanEqualAndState(dto.getStartAt(),dto.getEndAt(),OrderState.ACTIVE)).thenReturn(15l);
        when(clientService.existsById(1l)).thenReturn(true);
        when(orderRepository.existsOrderAtThisDay(now.toLocalDate(), OrderState.ACTIVE)).thenReturn(null);
        when(timetableService.getByDayOfWeek(now.getDayOfWeek(),dto.getStartAt().toLocalDate()))
                .thenReturn(Optional.of(new Timetable(
                        LocalTime.of(15,0,0),
                        LocalTime.of(16,0,0),
                        now.getDayOfWeek(),
                        null
                )));

        try {
            orderService.reserve(dto);
        } catch (BadRequestException e) {
            assertEquals("There are no places available at this time",e.getMessage());
        }
    }

    @Test
    public void shouldReturnResponseDtoWhenReserveAndDatesAreCorrect() {
        LocalDateTime now = LocalDateTime.of(2024,1,1,15,0,0);
        OrderReserveRqDto dto = new OrderReserveRqDto(1l,now,now.plusHours(1));

        Client client = new Client();
        client.setId(dto.getClientId());

        Order order = new Order(dto.getStartAt(),dto.getEndAt(),OrderState.ACTIVE,client);
        order.setId(1l);

        OrderReserveRsDto reserveRsDto = new OrderReserveRsDto(1l);

        when(orderMapper.map(Mockito.any())).thenReturn(reserveRsDto);
        when(orderRepository.save(Mockito.any())).thenReturn(order);
        when(clientService.getReferenceById(dto.getClientId())).thenReturn(client);
        when(orderRepository.countByStartAtGreaterThanEqualAndEndAtLessThanEqualAndState(dto.getStartAt(),dto.getEndAt(),OrderState.ACTIVE)).thenReturn(0l);
        when(clientService.existsById(1l)).thenReturn(true);
        when(orderRepository.existsOrderAtThisDay(now.toLocalDate(), OrderState.ACTIVE)).thenReturn(null);
        when(timetableService.getByDayOfWeek(now.getDayOfWeek(),dto.getStartAt().toLocalDate()))
                .thenReturn(Optional.of(new Timetable(
                        LocalTime.of(15,0,0),
                        LocalTime.of(16,0,0),
                        now.getDayOfWeek(),
                        null
                )));

        assertEquals(reserveRsDto,orderService.reserve(dto));
    }

    @Test
    public void shouldReturnEmptyListWhenNoTimetableOnThisDay() {
        LocalDate localDate = LocalDate.of(2024,12,4);
        when(timetableService.getByDayOfWeek(localDate.getDayOfWeek(),localDate))
                .thenReturn(Optional.empty());

        assertEquals(0,orderService.getAll(localDate,false).size());
    }

    @Test
    public void shouldListWith1OrderWhenTimetableExistsAndOrdersNotEmptyAndAvailableIsFalse() {
        LocalDate date = LocalDate.of(2024,12,4);
        when(timetableService.getByDayOfWeek(date.getDayOfWeek(),date))
                .thenReturn(Optional.of(new Timetable(
                        LocalTime.of(15,0,0),
                        LocalTime.of(16,0,0),
                        date.getDayOfWeek(),
                        null
                )));

        Order order = new Order();
        order.setStartAt(LocalDateTime.of(date,LocalTime.of(15,0,0)));
        order.setEndAt(LocalDateTime.of(date,LocalTime.of(16,0,0)));
        when(orderRepository.findAllByDateAndState(date,OrderState.ACTIVE)).thenReturn(List.of(
                order
        ));

        List<OrderRsDto> result = orderService.getAll(date,false);

        assertEquals(1,result.size());
        assertEquals(1,result.get(0).getCount());
    }

    @Test
    public void shouldListWith9AvailableOrdersWhenTimetableExistsAndOrdersNotEmptyAndAvailableIsTrue() {
        LocalDate date = LocalDate.of(2024,12,4);
        when(timetableService.getByDayOfWeek(date.getDayOfWeek(),date))
                .thenReturn(Optional.of(new Timetable(
                        LocalTime.of(15,0,0),
                        LocalTime.of(16,0,0),
                        date.getDayOfWeek(),
                        null
                )));

        Order order = new Order();
        order.setStartAt(LocalDateTime.of(date,LocalTime.of(15,0,0)));
        order.setEndAt(LocalDateTime.of(date,LocalTime.of(16,0,0)));
        when(orderRepository.findAllByDateAndState(date,OrderState.ACTIVE)).thenReturn(List.of(
                order
        ));

        List<OrderRsDto> result = orderService.getAll(date,true);

        assertEquals(1,result.size());
        assertEquals(9,result.get(0).getCount());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenOrderWithThisClientIdAndOrderIdNotExists() {
        OrderCancelRqDto dto = new OrderCancelRqDto(1l,1l);

        when(orderRepository.findByIdAndClientId(dto.getOrderId(), dto.getClientId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> orderService.cancelOrder(dto));
    }

    @Test
    public void shouldUpdateOrderStateToCanceledWhenOrderWithThisClientIdAndOrderIdExists() {
        OrderCancelRqDto dto = new OrderCancelRqDto(1l,1l);

        Order order = new Order();
        order.setState(OrderState.ACTIVE);

        when(orderRepository.findByIdAndClientId(dto.getOrderId(), dto.getClientId())).thenReturn(Optional.of(order));

        orderService.cancelOrder(dto);

        assertEquals(OrderState.CANCELED,order.getState());
    }
}
