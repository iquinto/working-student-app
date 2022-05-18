package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.JobPosition
import com.iquinto.workingstudent.model.Notification
import com.iquinto.workingstudent.model.Slot
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.University
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class NotificationSpec extends Specification {

    Area area
    Address address
    Employer employer
    University university
    Set <Slot> slots = new HashSet<>();
    Set <JobPosition> jobPositions = new HashSet<>();

    Student student
    JobPosition jobPosition

    def setup(){
        university = new University("UOC")
        area  = new Area("Informática")
        jobPositions.add(new JobPosition("Programador"))
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
        slots.add(new Slot(DaysOfTheWeek.MARTES,09.00, 09.59));

         student = new Student("test_student1",  "test_student1@gmail.com",
                address, Role.ROLE_STUDENT);
        student.setName("Maria");
        student.setSurname("Diana");
        student.setPhone("6555343321");
        student.setBirthday(LocalDate.parse("2000-01-08"));
        student.setSex("Female");
        student.setRole(Role.ROLE_STUDENT);
        student.setDescription("Some description");
        student.setUniversity(university);
        student.setStudentId("245678954");
        student.setPassword("WhatTheFuck");
        student.setJobPositions(jobPositions);
    }

    @Unroll
    def "test create new notification object #type"(){
        when:
        Notification notification = new  Notification(employer, student, type, "Some messafe");
        notification.setSlots(slots);

        then:
        notification != null
        notification.subject == type

        where:
        type << [Notification.SAVE, Notification.ACCEPTED,
                 Notification.DELETE,Notification.EDIT, Notification.REJECTED]
    }

    def "test  getter and setter"(){
        when:
        Notification notification = new  Notification();
        notification.id = 1L
        notification.setOrigin(employer)
        notification.setDestination(student)
        notification.setSubject(type)
        notification.setMessage('test')
        notification.setCreated(LocalDate.now())
        notification.setIcon('ex')
        notification.setColor('ex')
        notification.setIconAndColor('test')
        notification.setSlots(slots);

        then:
        notification != null
        notification.subject == type

        where:
        type << [Notification.SAVE, Notification.ACCEPTED,
                 Notification.DELETE,Notification.EDIT, Notification.REJECTED]
    }
}
