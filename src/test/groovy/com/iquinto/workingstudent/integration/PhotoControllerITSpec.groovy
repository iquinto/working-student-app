package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.PhotoController
import com.iquinto.workingstudent.model.User
import com.iquinto.workingstudent.service.PhotoStorageService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import com.iquinto.workingstudent.utils.WithMockCustomUser
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Ignore
import spock.lang.Stepwise

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoControllerITSpec extends SharedConfigSpecification {

    private final static String BASE_URL = "/photo"

    @WithMockUser(username = TEST_USER_STUDENT)
    def "test getProfilePicture"() {
        when:
        ResultActions response = mockMvc.perform(get("${BASE_URL}/avatar")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(PhotoController))
        response.andExpect(handler().methodName("getProfilePicture"))
        response.andExpect(status().isOk())
    }

    @WithMockUser(username = 'test_student5')
    def "test getProfilePicture no picture"() {
        when:
        ResultActions response = mockMvc.perform(get("${BASE_URL}/avatar")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(PhotoController))
        response.andExpect(handler().methodName("getProfilePicture"))
        response.andExpect(status().isOk())
    }


    @WithMockUser()
    def "test getAvatarByUsername"() {
        when:
        ResultActions response = mockMvc.perform(get("${BASE_URL}/avatar/${username}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(PhotoController))
        response.andExpect(handler().methodName("getAvatarByUsername"))
        result ?  response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())

        where:
        username                || result
        TEST_USER_STUDENT       || true
        'xxx'                   || false
        'test_student5'         || true
    }

    @WithMockCustomUser(username = 'test_employer1')
    def "test setProfilePic action"(){
        setup:
        User user = userService.findByUsername("test_employer1")
        MockMultipartFile file  = new MockMultipartFile(
                "file",
                "avatar.png",
                "img/png",
                "Some photo".getBytes()
        );

        when: 'upload avatar'
        ResultActions response = mockMvc.perform(multipart(BASE_URL + "/setProfilePicture")
                .file(file).header("Authorization", "BearereyJhbGciOiJIUzUxMiJ9")
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(handler().handlerType(PhotoController))
        response.andExpect(handler().methodName("setProfilePic"))
        response.andExpect(status().isOk())
    }

}
