package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.JobPosition
import spock.lang.Specification
import spock.lang.Unroll

class JobPositionSpec extends Specification {

     @Unroll
    def "test create new jobPosition object #name"(){
        when:
        JobPosition jobPosition = new JobPosition(name)

        then:
        jobPosition != null
        jobPosition.name == name

        where:
        name << ["Camarero", "Cocinero", "Profesor de baile", "Profesor de mates", "Profesor de idioma", "Conductor",
                 "Programador", "Profesor de deportes", "Electricista"]
    }

    @Unroll
    def "test create new jobPosition object 2 #name"(){
        when:
        JobPosition jobPosition = new JobPosition()
        jobPosition.setId(1L)
        jobPosition.setName(name)

        then:
        jobPosition != null
        jobPosition.name == name
        jobPosition.toString() != null

        where:
        name << ["Camarero", "Cocinero", "Profesor de baile", "Profesor de mates", "Profesor de idioma", "Conductor",
                 "Programador", "Profesor de deportes", "Electricista"]
    }
}
