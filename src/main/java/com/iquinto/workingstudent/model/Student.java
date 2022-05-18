package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iquinto.workingstudent.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="student")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "student_id", nullable = false, length = 30)
    private String studentId;

    @Column(name = "has_car", nullable = false)
    private boolean hasCar;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(	name = "student_job_position",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "job_position_id"))
    private Set<JobPosition> jobPositions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "university_id", referencedColumnName = "id", nullable = false)
    private University university;

    @OneToMany(mappedBy="student")
    private Set<Rating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy="student")
    private Set<Resume> resumes;

    @JsonIgnore
    @OneToMany(mappedBy="student")
    private Set<Schedule> schedules;

    public Student(){}

    public Student(String username,  String email, Address address, Role role) {
        super(username,  email, address, role);
    }

    public boolean isHasCar() {
        return hasCar;
    }

    public void setHasCar(boolean haCar) {
        this.hasCar = haCar;
    }

    public void addResume(Resume resume) {
        resumes.add(resume);
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for (JobPosition c : this.getJobPositions()){
            list.add(c.getName());
        }

        return " [" +
                "  username: " + this.getUsername() +
                "  email: " + this.getEmail() +
                "  username: " + this.getName() +
                "  address: " + this.getAddress().toString() +
                "  categories: " + list +
                "]" ;

    }



}
