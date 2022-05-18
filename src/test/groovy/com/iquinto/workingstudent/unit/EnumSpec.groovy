package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import com.iquinto.workingstudent.model.enums.State
import spock.lang.Specification
import spock.lang.Unroll

class EnumSpec extends Specification{

    def setup(){
    }

    @Unroll
    def "test Role"() {
        expect:
        Role.ROLE_STUDENT != null
    }

    @Unroll
    def "test DaysOfTheWeek getText"() {
        expect:
        DaysOfTheWeek.DOMINGO.getText() == "Domingo"
    }

    @Unroll
    def "test DaysOfTheWeek fromString"() {
        expect:
        DaysOfTheWeek.fromString(input) == output

        where:
        input    || output
        "Sabado" || DaysOfTheWeek.SABADO
        "XXX"    || null
    }

    @Unroll
    def "test Province getText"() {
        expect:
        Province.TOLEDO.getText() == "Toledo"
    }

    @Unroll
    def "test Province fromString"() {
        expect:
        Province.fromString(input) == output

        where:
        input    || output
        "Valladolid" || Province.VALLADOLID
        "XXX"    || null
    }

    @Unroll
    def "test State getText"() {
        expect:
        State.AVAILABLE.getText() == "available"
    }


}
