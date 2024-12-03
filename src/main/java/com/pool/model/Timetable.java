package com.pool.model;

import com.pool.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "t_timetable")
public class Timetable extends BaseEntity {

    private LocalTime startAt;
    private LocalTime endAt;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalDate specifiedDay;

}
