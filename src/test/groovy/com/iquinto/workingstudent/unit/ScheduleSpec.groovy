package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.*
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

import java.time.LocalDate

class ScheduleSpec extends Specification {

    Area area;
    Address address;
    University university
    Student student
    Set<JobPosition> jobPositions = new HashSet<>()
    Slot slot


    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
        university = new University("UOC")
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
        slot = new Slot(DaysOfTheWeek.LUNES,09.00, 09.59)
    }

    def "est create new schedule object"(){
        when:
        Schedule schedule = new Schedule(student, slot)

        then:
        schedule != null
    }
}
