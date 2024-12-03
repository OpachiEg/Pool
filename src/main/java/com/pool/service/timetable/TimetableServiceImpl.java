package com.pool.service.timetable;

import com.pool.exception.BadRequestException;
import com.pool.exception.NotFoundException;
import com.pool.model.Timetable;
import com.pool.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    @Override
    public Optional<Timetable> getByDayOfWeek(DayOfWeek dayOfWeek, LocalDate date) {
        Optional<Timetable> optionalTimetable = findByDayOfWeekAndSpecifiedDay(dayOfWeek, date);
        if(optionalTimetable.isEmpty()) {
            optionalTimetable = findByDayOfWeek(dayOfWeek);
        }
        return optionalTimetable;
    }

    private Optional<Timetable> findByDayOfWeekAndSpecifiedDay(DayOfWeek dayOfWeek, LocalDate specifiedDay) {
        return timetableRepository.findFirstByDayOfWeekAndSpecifiedDay(dayOfWeek,specifiedDay);
    }

    private Optional<Timetable> findByDayOfWeek(DayOfWeek dayOfWeek) {
        return timetableRepository.findFirstByDayOfWeekAndSpecifiedDayIsNull(dayOfWeek);
    }
}
