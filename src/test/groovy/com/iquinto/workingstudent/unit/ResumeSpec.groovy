package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.JobPosition
import com.iquinto.workingstudent.model.Photo
import com.iquinto.workingstudent.model.Resume
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

class ResumeSpec extends Specification {

    Area area;
    Address address;
    Student student
    Set<JobPosition> jobPositions = new HashSet<>()

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
        student = new Student("test_employer2",  "broganzquinto@gmail.com", address, Role.ROLE_EMPLOYER);
        student.setName("Jun");
        student.setSurname("Alvarez");
        student.setPhone("6000745578");
        student.setDescription("Some desciption");
        student.setPassword("some password");
        student.setJobPositions(jobPositions)
    }

    def "test creat new resume object"(){
        when:
        Resume resume = new Resume("test.pdf", "application/pdf","some content".bytes)
        resume.setStudent(student)

        then:
        resume != null
        resume.getName() == "test.pdf"
        resume.getStudent() == student

    }

    def "test creat new photo object 1"(){
        when:
        Resume resume = new Resume()
        resume.setId('xxxx' as String)
        resume.setName("test.pdf" )
        resume.setType("application/pdf")
        resume.setData( "some content".bytes)

        then:
        resume != null
        resume.getName() == "test.pdf"
    }
}
