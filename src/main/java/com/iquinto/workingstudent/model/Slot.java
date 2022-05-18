package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "slot")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Slot  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DaysOfTheWeek day;

    @Column(name = "start_time", nullable = false)
    private Double startTime;

    @Column(name = "end_time", nullable = false)
    private Double endTime;

    @Column(name = "time_equivalent")
    private double timeEquivalent  = 1.00;

    @JsonIgnore
    @OneToMany(mappedBy="slot")
    private Set<Schedule> schedules;

    public Slot() { }

    public Slot(DaysOfTheWeek day, Double startTIme, Double endTime) {
        this.day = day;
        this.startTime = startTIme;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return " [" +
                "day='" + day + '\'' +
                ", start=" + startTime +
                ", end=" + endTime +
                ']';
    }
}
