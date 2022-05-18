package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.*
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

import java.time.LocalDate

class ReservationSpec extends Specification {

    Area area;
    Address address;
    Student student
    Employer employer
    Set<JobPosition> jobPositions = new HashSet<>()
    Slot slot
    Schedule schedule
    University university

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())

        university = new University("UOC")

        student = new Student("test_student",  "broganzquinto@gmail.com", address, Role.ROLE_STUDENT);
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

        employer = new Employer("test_employer2",  "broganzquinto@gmail.com", address, Role.ROLE_EMPLOYER);
        employer.setName("Jun");
        employer.setSurname("Alvarez");
        employer.setPhone("6000745578");
        employer.setDescription("Some desciption");
        employer.setWebsite("www.prova.com");
        employer.setPassword("some password");
        employer.setArea(area);
        employer.setHasCompany(true);

        slot = new Slot(DaysOfTheWeek.LUNES,09.00, 09.59)
        schedule = new Schedule(student, slot)
    }

    def "est create new reservation object"(){
        when:
        Reservation reservation = new Reservation(employer, LocalDate.now(), 10)

        then:
        reservation != null
    }
}
