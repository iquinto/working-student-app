package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Area
import spock.lang.Specification
import spock.lang.Unroll

class AreaSpec extends Specification{

    def setup(){
    }

    def "test create new area object 1"() {
        when:
        Area area  = new Area();
        area.setName('prova')
        area.setId(1L)

        then:
        area != null
        area.id != null
        area.name == 'prova'
        area.toString() != null
    }

    def "test create new area object 2"() {
        when:
        Area area  = new Area('prova2');
        then:
        area != null

    }



}
