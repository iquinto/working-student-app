package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.ReservationController
import com.iquinto.workingstudent.model.Reservation
import com.iquinto.workingstudent.model.Schedule
import com.iquinto.workingstudent.model.Slot
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek
import com.iquinto.workingstudent.service.SlotService
import com.iquinto.workingstudent.service.StudentService
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerITSpec extends SharedConfigSpecification {

    @Autowired SlotService slotService

    @WithMockUser
    def "test list"(){
        when:
        ResultActions response = mockMvc.perform(get("/reservation/list/${username}")
                .param('page','1')
                .param('size','1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("list"))
        response.andExpect(status().isOk())

        where:
        username << [TEST_USER_STUDENT, TEST_USER_EMPLOYER]
    }


    @WithMockUser
    def "test saveReservation"(){
        given:
        Student s = studentService.findByUsername(TEST_USER_STUDENT)
        def slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.JUEVES, 10.00, 10.59)
        Schedule schedule = new Schedule(s, slot)
        scheduleService.save(schedule)

        Map map = [:]
        map.student = s.getUsername()
        map.schedules = [slot.id]
        def json = new JsonBuilder()
        json rootKey: map

        when:
        ResultActions response = mockMvc.perform(post("/reservation/save/${username}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(map))
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("saveReservation"))
        found ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())

        where:
        username                || found
        TEST_USER_EMPLOYER      || true
        'xxx'                   || false
    }

    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test getById"(){
        given:
        Slot slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 10.00, 10.59)
        Student student = studentService.findByUsername(TEST_USER_STUDENT)
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(student, slot))
        Reservation r = new Reservation()
        r.setStudent(student)
        r.setEmployer(employerService.findByUsername(TEST_USER_EMPLOYER))
        r.setNumberOfWeeks(22)
        r.setSchedules(schedules)
        reservationService.save(r)

        when:
        ResultActions response = mockMvc.perform(get("/reservation//get/${r.getId()}")
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("getById"))
        response.andExpect(status().isOk())
    }


    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test update"(){
        given:
        Slot slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 11.00, 11.59)
        Student student = studentService.findByUsername(TEST_USER_STUDENT)
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(student, slot))
        Reservation r = new Reservation()
        r.setStudent(student)
        r.setEmployer(employerService.findByUsername(TEST_USER_EMPLOYER))
        r.setNumberOfWeeks(22)
        r.setSchedules(schedules)
        r=reservationService.save(r)

        Map map = [:]
        map.student = student.getUsername()
        map.schedules = [slot.id]
        def json = new JsonBuilder()
        json rootKey: map

        when:
        ResultActions response = mockMvc.perform(put("/reservation/edit/id/${r.getId()}/user/${TEST_USER_EMPLOYER}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(map))
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("updateReservation"))
        response.andExpect(status().isOk())
    }



    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test reject"(){
        given:
        Slot slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 13.00, 13.59)
        Student student = studentService.findByUsername(TEST_USER_STUDENT)
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(student, slot))
        Reservation r = new Reservation()
        r.setStudent(student)
        r.setEmployer(employerService.findByUsername(TEST_USER_EMPLOYER))
        r.setNumberOfWeeks(22)
        r.setSchedules(schedules)
        r = reservationService.save(r)

        when:
        ResultActions response = mockMvc.perform(delete("/reservation/reject/id/${r.getId()}")
                .contentType(MediaType.APPLICATION_JSON)
                .param('rate','5')
                .param('comment','comment')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("rejectReservation"))
        response.andExpect(status().isOk())
    }

    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test delete"(){
        given:
        Slot slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 13.00, 13.59)
        Student student = studentService.findByUsername(TEST_USER_STUDENT)
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(student, slot))
        Reservation r = new Reservation()
        r.setStudent(student)
        r.setStartDate(LocalDate.now())
        r.setEndDate()
        r.setEmployer(employerService.findByUsername(TEST_USER_EMPLOYER))
        r.setNumberOfWeeks(22)
        r.setSchedules(schedules)
        r = reservationService.save(r)

        when:
        ResultActions response = mockMvc.perform(delete("/reservation/delete/id/${r.getId()}")
                .contentType(MediaType.APPLICATION_JSON)
                .param('rate','5')
                .param('comment','comment')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("deleteReservation"))
        response.andExpect(status().isOk())
    }

    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test accept"(){
        given:
        Slot slot = slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 15.00, 15.59)
        Student student = studentService.findByUsername(TEST_USER_STUDENT)
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule(student, slot))
        Reservation r = new Reservation()
        r.setStudent(student)
        r.setStartDate(LocalDate.now())
        r.setEndDate()
        r.setEmployer(employerService.findByUsername(TEST_USER_EMPLOYER))
        r.setNumberOfWeeks(22)
        r.setSchedules(schedules)
        r = reservationService.save(r)

        when:
        ResultActions response = mockMvc.perform(put("/reservation/accept/id/${r.getId()}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ReservationController))
        response.andExpect(handler().methodName("acceptReservation"))
        response.andExpect(status().isOk())
    }
}
