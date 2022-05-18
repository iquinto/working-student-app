package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "university")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public  class University implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    public University() { }

    public University(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " [" +
                "id=" + id +
                ", name='" + name + '\'' +
                ']';
    }
}


