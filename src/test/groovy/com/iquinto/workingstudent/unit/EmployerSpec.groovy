package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import spock.lang.Specification

class EmployerSpec extends Specification{

    Area area;
    Address address;

    def setup(){
        area  = new Area("Informática")
        address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText())
    }

    def "test create new employer object"(){
        when:
        Employer employer = new Employer("test_employer2",  "broganzquinto@gmail.com", address, Role.ROLE_EMPLOYER);
        employer.setName("Jun");
        employer.setSurname("Alvarez");
        employer.setPhone("6000745578");
        employer.setDescription("Some desciption");
        employer.setWebsite("www.prova.com");
        employer.setPassword("some password");
        employer.setArea(area);
        employer.setHasCompany(true);

        then:
        employer != null
        employer.area == area
        employer.address == address
    }
}
