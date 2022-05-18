package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class JobPostRequest {
    private String type; // temporary / internship
    private String title;
    private String requirements;
    private String description;
    private LocalDate startDate;
    private Long category;
    private Double yearSalary;
    private LocalDate expiration;
}
