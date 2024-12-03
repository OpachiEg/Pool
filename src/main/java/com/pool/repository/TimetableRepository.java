package com.pool.repository;

import com.pool.model.Timetable;
import com.pool.repository.base.BaseRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

public interface TimetableRepository extends BaseRepository<Timetable> {

    Optional<Timetable> findFirstByDayOfWeekAndSpecifiedDay(DayOfWeek dayOfWeek, LocalDate localDate);

    Optional<Timetable> findFirstByDayOfWeekAndSpecifiedDayIsNull(DayOfWeek dayOfWeek);

}
