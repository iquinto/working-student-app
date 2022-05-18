package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public  class Reservation  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_Date")
    private LocalDate startDate;

    @Column(name = "end_Date")
    private LocalDate endDate;

    @Column(name = "accepted")
    private boolean accepted = false;

    @Column(name = "number_of_weeks")
    private int numberOfWeeks;

    @Column(name = "total_hours")
    private double totalHours;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(	name = "reservation_schedule",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private Set<Schedule> schedules;

    public Reservation() { }

    public Reservation(@NotNull Employer employer, @NotNull LocalDate startDate, int numberOfWeeks) {
        this.employer = employer;
        this.startDate = startDate;
        this.numberOfWeeks = numberOfWeeks;
        this.setEndDate();
    }

    public  void setEndDate(){
        LocalDate date = this.startDate;
        this.endDate = date.plusDays(this.numberOfWeeks * 7L);
    }

    @Override
    public String toString() {
        int sch = 0;
        if(schedules.size() > 0 ) {
            sch = schedules.size();
        }
        return " [" +
                "id=" + this.id +
                " employer=" + employer.getUsername() +
                " student=" + student.getUsername() +
                " startDate=" + startDate +
                " numberOfWeeks=" + numberOfWeeks+
                " numSchedules =" + sch +
                ']';
    }

}


