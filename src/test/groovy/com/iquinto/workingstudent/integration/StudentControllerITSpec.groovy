package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.AuthenticationController
import com.iquinto.workingstudent.controller.StudentController
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.RequestParam
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerITSpec extends SharedConfigSpecification {

    @WithMockUser
    def "test register list 1"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list 2"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list").param("jobPosition", '1')
                .param("jobPosition", '1')
                .param("city", 'a')
                .param("day", 'Sabado')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }


    @WithMockUser
    def "test register list 3"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list")
                .param("city", 'a')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list 4"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list")
                .param("day", 'Sabado')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list 5"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list").param("jobPosition", '1')
                .param("jobPosition", '1')
                .param("day", 'Sabado')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }


    @WithMockUser
    def "test register list 6"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list").param("jobPosition", '1')
                .param("city", 'a')
                .param("day", 'Sabado')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list 7"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list").param("jobPosition", '1')
                .param("city", 'a')
                .param("jobPosition", '1')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list 8"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list").param("jobPosition", '1')
                .param("jobPosition", '1')
                .param("startTime", '17.00')
                .param("endTime", '23.00')
                .param("page", '1')
                .param("size", '1')
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("list"))
    }


    @WithMockUser
    def "test  show"() {
        when:
        ResultActions response = mockMvc.perform(get("/student/show/${username}").param("jobPosition", '1')
                .param("page", '1')
                .param("size", '1')
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        result ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("show"))

        where:
        username            || result
        TEST_USER_STUDENT   || true
        'xxx'               || false
    }


    @WithMockUser
    def "test  lisContracted"(){
        when:
        ResultActions response = mockMvc.perform(get("/student/list/contracted").param("jobPosition", '1')
                .param("page", '1')
                .param("size", '1')
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(StudentController))
        response.andExpect(handler().methodName("listContractred"))
    }

}
