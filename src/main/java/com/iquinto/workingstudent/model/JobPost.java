package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="job_post")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employer employer;

    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Type(type = "text")
    @Column(name = "requirements", nullable = false)
    private String requirements;

    @Lob
    @Column(name = "description", nullable = false)
    @Type(type = "text")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate = LocalDate.now();

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(	name = "job_post_area",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id"))
    private Area category;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(	name = "job_post_student",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> candidates;


    @Column(name = "year_salary", nullable = false)
    private Double yearSalary;

    @Column(name = "expiration")
    private LocalDate expiration;


    public void addCandidate(Student student){
        candidates.add(student);
    }




    @Override
    public String toString() {
        return " [" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", category=" + category.getName() +
                ", yearSalary=" + yearSalary +
                ", expiration=" + expiration +
                ", employer=" + employer.getName() +
                ']';
    }
}
