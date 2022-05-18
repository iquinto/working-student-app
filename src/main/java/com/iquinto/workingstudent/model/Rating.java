package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="rating")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rating  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "rate", nullable = false)
    private int rate = 1;

    @Column(name = "comment")
    private String comment ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Rating(){}

    public Rating(Long reservationId, int rate, Student student) {
        this.reservationId = reservationId;
        this.rate = rate;
        this.student = student;
    }

    public Rating(int rate, Student student) {
        this.rate = rate;
        this.student = student;
    }


    @Override
    public String toString() {
        return " [" +
                "transactionId=" + reservationId +
                ", rate=" + rate +
                ", student=" + student.getUsername() +
                ']';
    }
}
