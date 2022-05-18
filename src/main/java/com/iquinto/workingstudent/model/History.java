package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class History  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "month", nullable = false)
    private int month;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    public History(){}

    public History(String username, Long reservationId, int month, int year, LocalDate dateCreated) {
        this.username = username;
        this.reservationId = reservationId;
        this.month = month;
        this.year = year;
        this.dateCreated = dateCreated;
    }
}
