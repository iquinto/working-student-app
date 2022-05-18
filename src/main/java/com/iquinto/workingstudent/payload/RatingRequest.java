package com.iquinto.workingstudent.payload;

import com.iquinto.workingstudent.model.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {

    private Long reservationId;

    private int rate = 1;

    private String comment;

    private String username;

}