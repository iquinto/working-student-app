package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.University
import com.iquinto.workingstudent.model.enums.Role
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class UserSpec extends Specification{

    University university

    def setup(){
        university = new University("UOC")
    }

    @Unroll
    def 'test initialize  student #username'() {
        when:
        Student student = new Student(username, email, SharedConfigSpecification.INIT_ADDRESS(), Role.ROLE_STUDENT);
        student.setName("Isagani");
        student.setSurname("Quinto");
        student.setPhone("640075567");
        student.setPassword("somepass");
        student.setBirthday(LocalDate.of(2000, 12,12));
        student.setSex("Male");
        student.setDescription("Some text");
        student.setStudentId("245678954");
        student.setUniversity(university)
        student.setJobPositions(SharedConfigSpecification.INIT_JOBPOSITION_LIST());

        then:
        student.username == username
        student.email == email

        where:
        username    | email
        'iquinto'   | 'iquinto@gmail.com'
        'broganz'   | 'broganz@gmail.com'
        'spock'     | 'spock@gmail.com'
        'junit'     | 'junit@gmail.com'
    }

    @Unroll
    def 'test initialize employer'() {
        when:
        Employer employer = new Employer(username, email, SharedConfigSpecification.INIT_ADDRESS(), Role.ROLE_STUDENT);
        employer.setName("Isagani");
        employer.setSurname("Quinto");
        employer.setPhone("640075567");
        employer.setPassword("somepass");
        employer.setArea(SharedConfigSpecification.INIT_AREA_LIST()?.first());
        employer.hasCompany = true

        then:
        employer.username == username
        employer.email == email

        where:
        username    | email
        'iquinto'   | 'iquinto@gmail.com'
        'broganz'   | 'broganz@gmail.com'
        'spock'     | 'spock@gmail.com'
        'junit'     | 'junit@gmail.com'
    }


}
