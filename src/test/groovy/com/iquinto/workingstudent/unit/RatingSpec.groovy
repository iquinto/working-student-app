package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.JobPosition
import com.iquinto.workingstudent.model.Rating
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

class RatingSpec extends Specification {

    Area area;
    Address address;
    Student student
    Set<JobPosition> jobPositions = new HashSet<>()

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
        student = new Student("test_student",  "broganzquinto@gmail.com", address, Role.ROLE_STUDENT);
        student.setName("Jun");
        student.setSurname("Alvarez");
        student.setPhone("6000745578");
        student.setDescription("Some desciption");
        student.setPassword("some password");
        student.setJobPositions(jobPositions)
    }

    def "est create new rating object"(){
        when:
        Rating rating = new Rating(1L, 5, student)

        then:
        rating != null
    }

    def "test gestter and setter"(){
        when:
        Rating rating = new Rating(5, student)
        rating.setId(5L)
        rating.setReservationId(1L)
        rating.setStudent(student)
        rating.setRate(5)
        then:

        rating != null
        rating.student != null
    }
}
