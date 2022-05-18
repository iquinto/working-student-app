package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.AddressController
import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.payload.MessageResponse
import com.iquinto.workingstudent.service.AddressService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressControllerITSpec extends SharedConfigSpecification {

    @Autowired AddressService addressService

    @Unroll
    @WithMockUser()
    def "test save action"() {
        given:
        Map params  =  [
                street: "Calle Pere 24",
                city: "Cambrils",
                province: Province.TARRAGONA,
                zipcode: "43850",
                country: "España"
        ]
        def json = new JsonBuilder()
        json rootKey: params

        when:
        ResultActions response = mockMvc.perform(post("/address/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AddressController))
        response.andExpect(handler().methodName("save"))

        and:
        jsonResult.street == params.street
        jsonResult.city == params.city
        jsonResult.zipcode == params.zipcode
    }

    @WithMockUser()
    def "test delete action"() {
        given:
        Address address =  addressService.save(new Address(
                street: "Calle Pere 33",
                city: "Cambrils",
                province: Province.TARRAGONA,
                zipcode: "43850",
                country: "España"))

        when:
        ResultActions response = mockMvc.perform(post("/address/delete/${address.id}"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AddressController))
        response.andExpect(handler().methodName("delete"))

        and:
        jsonResult.message == MessageResponse.DELETE_CORRECTLY
    }
}
