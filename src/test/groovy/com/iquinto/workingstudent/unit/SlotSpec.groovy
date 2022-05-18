package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.*
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

import java.time.LocalDate

class SlotSpec extends Specification {

    Area area;
    Address address;
    Student student
    Set<JobPosition> jobPositions = new HashSet<>()
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
    }

    def "test create new slot object"(){
        when:
        Slot slot = new Slot(DaysOfTheWeek.LUNES,09.00, 09.59)

        then:
        slot != null
    }

    def "test create new slot object 2"(){
        when:
        Slot slot = new Slot()
        slot.setId(1L)
        slot.setDay(DaysOfTheWeek.LUNES)
        slot.setStartTime(09.00)
        slot.setEndTime(9.59)
        slot.setTimeEquivalent(8.00)

        then:
        slot != null
    }
}
