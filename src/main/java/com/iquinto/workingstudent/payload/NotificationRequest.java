package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NotificationRequest {

    private Long id;
    private boolean read;
    private Date created;

}
