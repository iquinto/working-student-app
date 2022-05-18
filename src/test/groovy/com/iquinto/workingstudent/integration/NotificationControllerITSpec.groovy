package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.NotificationController
import com.iquinto.workingstudent.model.Notification
import com.iquinto.workingstudent.model.User
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import com.iquinto.workingstudent.utils.WithMockCustomUser
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Ignore
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationControllerITSpec extends SharedConfigSpecification {

    private final static String BASE_URL = "/notification"

    @Unroll
    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test list action"() {
        when:
        ResultActions response = mockMvc.perform(get(BASE_URL + "/list")
                .param("all", input as String)
                .contentType(MediaType.APPLICATION_JSON))

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("list"))

        and:
        jsonResult.size() == result
        jsonResult.each { it ->
            assert it.destination.username == TEST_USER_STUDENT
        }


        where:
        input   || result
        0       || 5
        1       || 5
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test show action and notification not found"(){
        when:
        ResultActions response = mockMvc.perform(get(BASE_URL + "/show/${555.toLong()}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("show"))
        
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test show action"(){
        setup:
        User user = userService.findByUsername(TEST_USER_STUDENT)
        Notification notification = notificationService.findAllByDestinationAndRead(user, false)[0]

        when:
        ResultActions response = mockMvc.perform(get(BASE_URL + "/show/${notification.id}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("show"))

        and:
        jsonResult.size() > 0
        jsonResult.id == notification.id
        !jsonResult.read
        jsonResult.destination.username== TEST_USER_STUDENT
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test markAsRead incorrectly"(){
        when:
        ResultActions response = mockMvc.perform(put(BASE_URL + "/markAsRead/${55l.toLong()}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("markAsRead"))
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test markAsRead action"(){
        setup:
        User user = userService.findByUsername(TEST_USER_STUDENT)
        List<Notification> notifications = notificationService.findAllByDestinationAndRead(user, false)
        Notification notification =  notifications[0]

        when:
        ResultActions response = mockMvc.perform(put(BASE_URL + "/markAsRead/${notification.id}")
                .contentType(MediaType.APPLICATION_JSON))

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def result = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("markAsRead"))

        and:
        result == notifications.size() - 1
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test delete incorrectly"(){
        when:
        ResultActions response = mockMvc.perform(delete(BASE_URL + "/delete/${55l.toLong()}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("delete"))
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test delete action throw wxception"(){
        setup:
        User user = userService.findByUsername(TEST_USER_STUDENT)
        Notification notification = notificationService.findAllByDestinationAndRead(user, false)[0]

        when:
        ResultActions response = mockMvc.perform(delete(BASE_URL + "/delete/${notification.id}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def result = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("delete"))

        and:
        result.message == "Se ha eliminado correctamente la notificacion."
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test delete action"(){
        setup:
        User user = userService.findByUsername(TEST_USER_STUDENT)
        Notification notification = notificationService.findAllByDestinationAndRead(user, false)[0]

        when:
        ResultActions response = mockMvc.perform(delete(BASE_URL + "/delete/${notification.id}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def result = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(NotificationController))
        response.andExpect(handler().methodName("delete"))

        and:
        result.message == "Se ha eliminado correctamente la notificacion."
    }

}
