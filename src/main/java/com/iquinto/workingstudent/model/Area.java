package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "area")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true,length = 100)
    private String name;

    public Area() { }

    public Area(String name) {
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


