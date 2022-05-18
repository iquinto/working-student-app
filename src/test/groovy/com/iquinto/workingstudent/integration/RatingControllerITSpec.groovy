package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.RatingController
import com.iquinto.workingstudent.service.RatingService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonBuilder
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingControllerITSpec extends SharedConfigSpecification {

    @Unroll
    @WithMockUser()
    def "test save average"() {
        when:
        ResultActions response = mockMvc.perform(get("/rating/average/${input}"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(RatingController))
        response.andExpect(handler().methodName("average"))
        result ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())

        where:
        input             || result
        TEST_USER_STUDENT || true
        'xxx'             || false
        'test_student7'   || true

    }

    @Unroll
    @WithMockUser()
    def "test save"() {
        given:
        Map map = [:]
        map.reservationId = 1L
        map.rate = 3
        map.comment = "test"
        map.username = input
        def json = new JsonBuilder()
        json rootKey: map

        when:
        ResultActions response = mockMvc.perform(post("/rating/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(map)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(RatingController))
        response.andExpect(handler().methodName("save"))
        result ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())

        where:
        input             || result
        TEST_USER_STUDENT || true
        'xxx'             || false
    }
}