package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationRequest {
    private List<Long> schedules;
    private String student;
    private LocalDate startDate = LocalDate.now();
    private int numberOfWeeks;
}
