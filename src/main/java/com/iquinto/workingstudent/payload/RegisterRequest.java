package com.iquinto.workingstudent.payload;

import com.iquinto.workingstudent.model.Address;
import com.iquinto.workingstudent.model.University;
import com.iquinto.workingstudent.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
public class RegisterRequest {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String phone;
    Role role;
    private Address address;

    // for password reset
    private String passwordConfirm;

    // STUDENT
    private String sex;
    private LocalDate birthday;
    private String description;
    private Long university;
    private String studentId;
    private List<Long> jobPositions;

    private boolean hasCar;

    // Employer
    private boolean hasCompany;
    private Long area;
    private String website;

    @Override
    public String toString() {
        return "RegisterData{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
