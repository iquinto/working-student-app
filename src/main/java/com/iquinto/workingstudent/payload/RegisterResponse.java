package com.iquinto.workingstudent.payload;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
public class RegisterResponse {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Role role;
    private Address address;
    private String accessToken;
    private Photo photo;


    // STUDENT
    private String sex;
    private Date birthday;
    private String description;
    private University university;
    private String studentId;
    private Set<JobPosition> jobPositions;
    private boolean hasCar;

    // EMPLOYER
    private Area area;
    private boolean hasCompany;
    
    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    public void setHasCompany(boolean hasCompany) {
        hasCompany = hasCompany;
    }

    public RegisterResponse(Long id, String name, String surname, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accessToken = accessToken;
    }

    public  void addCategory(JobPosition jobPosition){
        this.jobPositions.add(jobPosition);
    }


}
