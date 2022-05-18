package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.enums.Province
import spock.lang.Specification

class AddressSpec extends Specification{

    def setup(){
    }

    def "test create new address object"() {
        when:
        Address address = new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText());

        then:
        address != null
    }
}
