package com.iquinto.workingstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iquinto.workingstudent.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="employer")
@PrimaryKeyJoinColumn(name = "id")
public class Employer extends User{

    @Column(name = "is_company")
    private boolean hasCompany = false;

    @Column(name = "website", length = 50)
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    @JsonIgnore
    @OneToMany(mappedBy="employer")
    private Set<Reservation> reservations;

    @JsonIgnore
    @OneToMany(mappedBy="employer")
    private Set<JobPost> jobPosts;

    public Employer(String username, String email, Address address, Role role) {
        super(username,  email, address, role);
    }
    public Employer() { }

    @Override
    public String toString() {

        return " [" +
                " username: " + this.getUsername() +
                "  email: " + this.getEmail() +
                "  username: " + this.getName() +
                "  address: " + this.getAddress().toString() +
                "  category: " + this.area.getName() +
                "]" ;

    }


}
