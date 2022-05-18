package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "notification")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Notification  implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static  String  DELETE = "baja";
    public final static  String SAVE = "alta";
    public final static  String EDIT = "modificación";
    public final static  String ACCEPTED = "confirmación";
    public final static  String REJECTED = "cancelación";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "subject", nullable = false, length = 50)
    private String subject;

    @Column(name = "read", nullable = false)
    private boolean read = false;

    @Column(name = "created", nullable = false)
    private LocalDate created = LocalDate.now();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(	name = "notification_slot",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    private Set<Slot> slots;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private User origin;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private User destination;

    @Column(name = "icon" , length = 20)
    private String icon;

    @Column(name = "color", length = 20)
    private String color;

    public Notification (User origin, User destination, String subject, String message){
        this.origin = origin;
        this.destination = destination;
        this.subject = subject;
        this.message = message;
        this.setIconAndColor(subject);
    }

    public Notification() {
    }

    public void setIconAndColor(String subject){
        if(subject == this.DELETE){
            this.icon = "arrow-down";
            this.color = "red";
        }else if(subject == this.SAVE){
            this.icon = "arrow-up";
            this.color = "green";
        }else if(subject == this.EDIT){
            this.icon = "pen";
            this.color = "orange";
        }else if(subject == this.ACCEPTED){
            this.icon = "check";
            this.color = "green";
        }else if(subject == this.REJECTED){
            this.icon = "trash";
            this.color = "red";
        } else {
            this.icon = "arrow-up";
            this.color = "green";
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                "subject='" + subject + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", icon='" + icon + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
