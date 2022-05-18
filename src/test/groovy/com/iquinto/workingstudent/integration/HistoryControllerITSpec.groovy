package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.EmployerController
import com.iquinto.workingstudent.controller.HistoryController
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoryControllerITSpec extends SharedConfigSpecification {

    @WithMockUser(username = "xxxx")
    def "test register show user not found"() {
        when:
        ResultActions response = mockMvc.perform(get("/history/list/2021")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(handler().handlerType(HistoryController))
        response.andExpect(handler().methodName("listTotalsByYear"))
        response.andExpect(status().isBadRequest())
        jsonResult.message == "El usuario no existe en el sistema"
    }

    @WithMockUser(username = TEST_USER_STUDENT)
    def "test register show correctly"() {
        when:
        ResultActions response = mockMvc.perform(get("/history/list/2021")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(handler().handlerType(HistoryController))
        response.andExpect(handler().methodName("listTotalsByYear"))
        response.andExpect(status().isOk())
        jsonResult == [3,2,2,1,1,1,1,1,2,1,1,3]
    }
}
