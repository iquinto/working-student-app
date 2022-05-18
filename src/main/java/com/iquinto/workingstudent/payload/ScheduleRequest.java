package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ScheduleRequest {
    private List<Long> slots;
    private List<Long> toScheduledList;

}
