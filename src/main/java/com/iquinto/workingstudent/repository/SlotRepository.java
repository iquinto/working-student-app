package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Slot;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findAll();
    Optional<Slot> findById(Long id);
    List<Slot> findAllByDay(DaysOfTheWeek day);
    List<Slot> findAllByStartTime(Double startTime);
    List<Slot> findAllByStartTimeGreaterThan(Double startTime);

    @Query("select s from Slot s where s.day = :day and s.startTime =:startTime and s.endTime = :endTime")
    Slot findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek day, Double startTime, Double endTime);


}
