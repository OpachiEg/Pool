package com.pool.service;

import com.pool.model.Timetable;
import com.pool.repository.TimetableRepository;
import com.pool.service.timetable.TimetableServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimetableServiceUnitTest {

    @Mock
    private TimetableRepository timetableRepository;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    @Test
    public void shouldReturnTimetableWithSpecifiedDayWhenTimetableWithSpecifiedDayExists() {
        LocalDate date = LocalDate.of(2024,12,4);

        Optional<Timetable> timetable = Optional.of(new Timetable());

        when(timetableRepository.findFirstByDayOfWeekAndSpecifiedDay(date.getDayOfWeek(),date)).thenReturn(timetable);

        assertEquals(timetable,timetableService.getByDayOfWeek(date.getDayOfWeek(),date));
    }

    @Test
    public void shouldReturnTimetableWithoutSpecifiedDayWhenTimetableWithSpecifiedDayNotExists() {
        LocalDate date = LocalDate.of(2024,12,4);

        Optional<Timetable> timetable = Optional.of(new Timetable());

        when(timetableRepository.findFirstByDayOfWeekAndSpecifiedDay(date.getDayOfWeek(),date)).thenReturn(Optional.empty());
        when(timetableRepository.findFirstByDayOfWeekAndSpecifiedDayIsNull(date.getDayOfWeek())).thenReturn(timetable);

        assertEquals(timetable,timetableService.getByDayOfWeek(date.getDayOfWeek(),date));
    }

}
