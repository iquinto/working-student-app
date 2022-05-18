package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="schedule")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "slot_id", referencedColumnName = "id")
    private Slot slot;

    private boolean reserve;

    public Schedule() {
    }

    public Schedule(Student student, Slot slot) {
        this.student = student;
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "student=" + student.getUsername() +
                ", slot=" + slot.getId() +
                ", reserve=" + reserve +
                '}';
    }
}
