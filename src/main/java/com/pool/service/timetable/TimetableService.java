package com.pool.service.timetable;

import com.pool.model.Timetable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

public interface TimetableService {

    Optional<Timetable> getByDayOfWeek(DayOfWeek dayOfWeek,LocalDate date);

}
