package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.ScheduleController
import com.iquinto.workingstudent.model.Schedule
import com.iquinto.workingstudent.model.Slot
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import com.iquinto.workingstudent.service.SlotService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Stepwise

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleControllerITSpec extends SharedConfigSpecification {

    @WithMockUser
    def "test listSlotsByDay"(){
        when:
        ResultActions response = mockMvc.perform(get("/schedule/slotsByDay"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("listSlotsByDay"))
        response.andExpect(status().isOk())
    }

    @WithMockUser
    def "test slotsByStartTime"(){
        when:
        ResultActions response = mockMvc.perform(get("/schedule/slotsByStartTime"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("slotsByStartTime"))
        response.andExpect(status().isOk())
    }


    @WithMockUser
    def "test listSlots"(){
        when:
        ResultActions response = mockMvc.perform(get("/schedule/slots"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("listSlots"))
        response.andExpect(status().isOk())
    }

    @WithMockUser
    def "test listSchedules"(){
        when:
        ResultActions response = mockMvc.perform(get("/schedule/list/${username}"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("listSchedules"))
        found ? response.andExpect(status().isOk()) :  response.andExpect(status().isBadRequest())

        where:
        username            || found
        TEST_USER_STUDENT   ||  true
        'xxxx'               || false
    }

    @Autowired SlotService slotService

    @WithMockUser
    def "test saveSchedules"(){
        given:
        Slot slot = new Slot(DaysOfTheWeek.DOMINGO, 8.00, 8.59)
        slotService.save(slot)
        def params = [
                slots: [slot.getId()]
        ]
        def json = new JsonBuilder()
        json rootKey: params

        when:
        ResultActions response = mockMvc.perform(post("/schedule/saveAll/${username}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("saveSchedules"))
        found ? response.andExpect(status().isOk()) :  response.andExpect(status().isBadRequest())

        where:
        username            || found
        'test_student2'     ||  true
        'xxxx'               || false
    }

    @WithMockUser
    def "test getScheduleByUserNameAndSlot"(){
        given:
        Student student = studentService.findByUsername(TEST_USER_STUDENT);
        Schedule schedule =  scheduleService.findAllByStudent(student)[0]

        when:
        ResultActions response = mockMvc.perform(get("/schedule/get/username/${username}/slot/${schedule.getSlot().getId()}"))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ScheduleController))
        response.andExpect(handler().methodName("getScheduleByUserNameAndSlot"))
        found ? response.andExpect(status().isOk()) :  response.andExpect(status().isBadRequest())

        where:
        username                || found
        TEST_USER_STUDENT       ||  true
        'xxxx'                  || false
    }

}
