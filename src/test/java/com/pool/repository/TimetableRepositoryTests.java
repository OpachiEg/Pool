package com.pool.repository;

import com.pool.annotation.DbTest;
import com.pool.config.DbTestContainerConfig;
import com.pool.model.Timetable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DbTest
public class TimetableRepositoryTests extends DbTestContainerConfig {

    @Autowired
    private TimetableRepository timetableRepository;

    @Test
    public void shouldReturnTimetableWhenFindByDayOfWeekAndSpecifiedDayAndTimetableExists() {
        Timetable timetable = new Timetable(LocalTime.now(),LocalTime.now(), DayOfWeek.MONDAY, LocalDate.now());
        timetable = timetableRepository.save(timetable);

        assertEquals(timetable,timetableRepository.findFirstByDayOfWeekAndSpecifiedDay(DayOfWeek.MONDAY,LocalDate.now()).get());
    }

    @Test
    public void shouldReturnTimetableWhenFindByDayOfWeekAndTimetableExists() {
        Timetable timetable = new Timetable(LocalTime.now(),LocalTime.now(), DayOfWeek.MONDAY, null);
        timetable = timetableRepository.save(timetable);

        assertEquals(timetable,timetableRepository.findFirstByDayOfWeekAndSpecifiedDayIsNull(DayOfWeek.MONDAY).get());
    }

}
