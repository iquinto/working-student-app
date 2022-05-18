package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.*
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

import java.time.LocalDate

class StudentSpec extends Specification {

    Area area;
    Address address;
    Set<JobPosition> jobPositions = new HashSet<>()
    University university

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
        university = new University("UOC")
    }

    def "est create new student object"(){
        when:
        Student student = new Student("test_student",  "broganzquinto@gmail.com", address, Role.ROLE_STUDENT);
        student.setName("Jun");
        student.setSurname("Alvarez");
        student.setPhone("6000745578");
        student.setDescription("Some desciption");
        student.setPassword("some password");
        student.setJobPositions(jobPositions)
        student.setBirthday(LocalDate.of(2000, 12,12));
        student.setSex("Male");
        student.setDescription("Some text");
        student.setStudentId("245678954");
        student.setUniversity(university)

        then:
        student != null
    }
}
