package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.AreaController
import com.iquinto.workingstudent.service.AddressService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AreaControllerITSpec extends SharedConfigSpecification {

    @Autowired AddressService addressService

    @Unroll
    @WithMockUser()
    def "test list action"() {
        when:
        ResultActions response = mockMvc.perform(get("/area/list"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AreaController))
        response.andExpect(handler().methodName("list"))

        and:
        jsonResult.size()  > 0
    }

}
