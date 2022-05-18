package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.University
import spock.lang.Specification
import spock.lang.Unroll

class UniversitySpec extends Specification{

    def setup(){
    }

    @Unroll
    def "test create new university object #name"() {
        when:
        University university  = new University(name)

        then:
        university != null
        university.name == name

        where:
        name << ["University1", "University2", "University3", "University4"]
    }

    def "test create new university object 2 #name"() {
        when:
        University university = new University()
        university.setId(1L)
        university.setName("LLL")


        then:
        university != null
        university.name == "LLL"

    }

}
