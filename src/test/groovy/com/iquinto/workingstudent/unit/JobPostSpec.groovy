package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.JobPost
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification
import spock.lang.Unroll
import java.time.LocalDate

class JobPostSpec extends Specification {

    Area area
    Address address
    Employer employer

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())

        employer = new Employer("test_employer2",  "broganzquinto@gmail.com", address , Role.ROLE_EMPLOYER);
        employer.setName("Jun");
        employer.setSurname("Alvarez");
        employer.setPhone("6000745578");
        employer.setDescription("Some desciption");
        employer.setWebsite("www.prova.com");
        employer.setPassword("some password");
        employer.setArea(area);
        employer.setHasCompany(true);
    }

    @Unroll
    def "test create new jobPost object"(){
        when:
        JobPost jobPost = new JobPost();
        jobPost.setId(1L)
        jobPost.setType(type);
        jobPost.setTitle("Practica Profesor de Danza");
        jobPost.setRequirements("Some Requirements");
        jobPost.setDescription("xxxx");
        jobPost.setStartDate(LocalDate.now());
        jobPost.setCategory(area);
        jobPost.setEmployer(employer);
        jobPost.setYearSalary(19000.00);
        jobPost.setExpiration(LocalDate.now().plusDays(10));
        jobPost.setPublicationDate(LocalDate.now().plusDays(10))

        then:
        jobPost != null
        jobPost.getType() == type

        where:
        type << ["internship",  "temporal"]

    }
}
