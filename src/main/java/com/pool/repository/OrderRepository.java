package com.pool.repository;

import com.pool.model.Order;
import com.pool.model.enums.OrderState;
import com.pool.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order> {

    long countByStartAtGreaterThanEqualAndEndAtLessThanEqualAndState(LocalDateTime startAt,LocalDateTime endAt,OrderState orderState);

    @Query("SELECT CASE WHEN o IS NULL THEN false ELSE true END FROM Order o WHERE DATE(o.startAt)=:date AND o.state=:state")
    Boolean existsOrderAtThisDay(LocalDate date,OrderState state);

    @Query("SELECT o FROM Order o WHERE DATE(o.startAt)=:date AND o.state=:state")
    List<Order> findAllByDateAndState(LocalDate date, OrderState state);

    Optional<Order> findByIdAndClientId(Long id, Long clientId);

}
