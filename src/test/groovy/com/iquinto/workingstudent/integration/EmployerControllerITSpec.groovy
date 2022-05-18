package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.EmployerController
import com.iquinto.workingstudent.controller.StudentController
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Ignore
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployerControllerITSpec extends SharedConfigSpecification {

    @Unroll
    @WithMockUser
    def "test show"() {
        when:
        ResultActions response = mockMvc.perform(get("/employer/show/${input}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(handler().handlerType(EmployerController))
        response.andExpect(handler().methodName("show"))
        pass ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())
        pass ? jsonResult.username == input :  jsonResult.message == 'No se ha encontrado el usuario  en el sistema'

        where:
        input            || pass
        'xxxx'           || false
        'test_employer1' || true

    }

}
