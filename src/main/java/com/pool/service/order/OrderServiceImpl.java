package com.pool.service.order;

import com.pool.dto.order.OrderCancelRqDto;
import com.pool.dto.order.OrderReserveRqDto;
import com.pool.dto.order.OrderReserveRsDto;
import com.pool.dto.order.OrderRsDto;
import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import com.pool.mapper.OrderMapper;
import com.pool.model.Order;
import com.pool.model.Timetable;
import com.pool.model.enums.OrderState;
import com.pool.repository.OrderRepository;
import com.pool.service.client.ClientService;
import com.pool.service.timetable.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TimetableService timetableService;
    private final ClientService clientService;
    private final OrderMapper orderMapper;

    @Value("${limits.max-orders-per-time}")
    private int maxOrdersPerTime;

    @Override
    public OrderReserveRsDto reserve(OrderReserveRqDto dto) {
        if (dto.getStartAt().isAfter(dto.getEndAt())) {
            throw new BadRequestException("Start time should be after end time");
        }

        long duration = ChronoUnit.MINUTES.between(dto.getStartAt(), dto.getEndAt());
        if (duration % 60 != 0 || duration == 0) {
            throw new BadRequestException("You can only book for 1,2,3, etc. hours");
        }

        if (dto.getStartAt().getMinute() != 0 || dto.getEndAt().getMinute() != 0) {
            throw new BadRequestException("Time must be discrete");
        }

        if(!clientService.existsById(dto.getClientId())) {
            throw new NotFoundException("Client not found");
        }

        if (orderRepository.existsOrderAtThisDay(dto.getStartAt().toLocalDate(),OrderState.ACTIVE) != null) {
            throw new BadRequestException("You can only make 1 entry per day");
        }

        DayOfWeek dayOfWeek = dto.getStartAt().getDayOfWeek();


        Optional<Timetable> optionalTimetable = timetableService.getByDayOfWeek(dayOfWeek, dto.getStartAt().toLocalDate());
        if (optionalTimetable.isEmpty()) {
            throw new BadRequestException("Order on this day is not available");
        }
        Timetable timetable = optionalTimetable.get();

        LocalDateTime timetableStartAt = timetable.getStartAt().atDate(dto.getStartAt().toLocalDate());
        LocalDateTime timetableEndAt = timetable.getEndAt().atDate(dto.getStartAt().toLocalDate());

        if (!isOrderInTimetableRange(dto.getStartAt(), dto.getEndAt(), timetableStartAt, timetableEndAt)) {
            throw new BadRequestException("You can only make an order during business hours");
        }

        long ordersPerTime = orderRepository.countByStartAtGreaterThanEqualAndEndAtLessThanEqualAndState(dto.getStartAt(), dto.getEndAt(),OrderState.ACTIVE);
        if (ordersPerTime >= maxOrdersPerTime) {
            throw new BadRequestException("There are no places available at this time");
        }

        Order order = new Order(dto.getStartAt(), dto.getEndAt(), OrderState.ACTIVE, clientService.getReferenceById(dto.getClientId()));

        return orderMapper.map(orderRepository.save(order));
    }

    @Override
    public List<OrderRsDto> getAll(LocalDate date, boolean available) {
        Optional<Timetable> optionalTimetable = timetableService.getByDayOfWeek(date.getDayOfWeek(), date);
        if (optionalTimetable.isEmpty()) {
            return new ArrayList<>();
        }
        Timetable timetable = optionalTimetable.get();

        List<Order> orders = orderRepository.findAllByDateAndState(date,OrderState.ACTIVE);

        List<OrderRsDto> result = new ArrayList<>();
        for (int i = 0; i <= ChronoUnit.HOURS.between(timetable.getStartAt(), timetable.getEndAt()); i++) {
            LocalDateTime timetableStartAt = LocalDateTime.of(date, LocalTime.of(timetable.getStartAt().getHour() + i, 0, 0));
            LocalDateTime timetableEndAt = LocalDateTime.of(date, LocalTime.of(timetable.getStartAt().getHour() + i + 1, 0, 0));

            long count = orders.stream().filter(o -> isOrderInTimetableRange(timetableStartAt, timetableEndAt, o.getStartAt(), o.getEndAt())).count();
            if (available) {
                count = maxOrdersPerTime - count;
            }

            result.add(new OrderRsDto(timetableStartAt, count));
        }

        return result;
    }

    @Override
    public void cancelOrder(OrderCancelRqDto dto) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndClientId(dto.getOrderId(), dto.getClientId());
        if (optionalOrder.isEmpty()) {
            throw new NotFoundException("Order not found");
        }
        Order order = optionalOrder.get();

        order.setState(OrderState.CANCELED);

        orderRepository.save(order);
    }

    private boolean isOrderInTimetableRange(LocalDateTime start, LocalDateTime end, LocalDateTime timetableStartAt, LocalDateTime timetableEndAt) {
        return ((start.isAfter(timetableStartAt) || start.isEqual(timetableStartAt)) && start.isBefore(timetableEndAt) &&
                end.isAfter(timetableStartAt) && (end.isBefore(timetableEndAt) || end.isEqual(timetableEndAt)));
    }
}
