package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.controller.AuthenticationController
import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.model.Employer
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import com.iquinto.workingstudent.utils.WithMockCustomUser
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

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerITSpec extends SharedConfigSpecification{

    @Unroll
    @WithMockUser
    def "test register  incorrectly"() {
        setup:
        def params = [
        name        : "Broganz",
        surname     : "Quinto",
        username    : username,
        password    : "1234567",
        phone       : "30000000",
        email       : email,
        birthday    : LocalDate.parse("2000-12-07"),
        sex         : "Male",
        role        : Role.ROLE_STUDENT,
        description : "some ",
        university  : 1.toLong(),
        studentId   : "7654321",
        address     : [
                street  : "Calle Pepito 24",
                city    : "Cambrils",
                province: Province.TARRAGONA,
                zipcode : "43850",
                country : "España"
        ],
        jobPositions: [1, 2]
        ]
        def json = new JsonBuilder()
        json rootKey: params

        when:
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("register"))
        jsonResult.message.contains(message)

        where:
        username        | email                      || message
        'test_student1' | 'broganzquinto@gmail.com'  || "Error: El usuario test_student1 ya existe en nuestro servidor!"
        'test_studentXX' | 'broganzquinto@gmail.com' || "Error: El correo broganzquinto@gmail.com ya existe en nuestro servidor!"
        'test_studentXX' | 'isa@gmail.com'           || "Error:  El correo debe ser de una universidad (.edu)"
    }

    @WithMockUser
    def "test register  action post [student]"(){
        setup:
        def json = new JsonBuilder()
        json rootKey: requestStudent

        when:
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(requestStudent)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("register"))

        and:
        jsonResult.message == "Se ha registrado correctamente el usuario. "
        Student student = studentService.findByUsername(requestStudent.username as String)
        student.username == requestStudent.username
        student.role == Role.ROLE_STUDENT
        student.jobPositions.size() == requestStudent.jobPositions?.size()
    }


    @WithMockUser
    def "test register  action post [employer]"(){
        setup:
        def json = new JsonBuilder()
        json rootKey: requestEmployer

        when:
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(requestEmployer)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("register"))

        and:
        jsonResult.message == "Se ha registrado correctamente el usuario. "
        Employer employer = employerService.findByUsername(requestEmployer.username as String)
        employer.username == requestEmployer.username
        employer.role == Role.ROLE_EMPLOYER
        employer.area.id == requestEmployer.area
    }

    @Unroll
    def 'test login with error #username'(){
        setup:
        Map login= [ username: username, password: TEST_PASSWORD]
        def json = new JsonBuilder()
        json rootKey: login

        when:
        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(login)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("login"))

        and:
        jsonResult.message.contains('Error')

        where:
        username << ['xxx', 'ggggg']
    }


    @Unroll
    def 'test login #username'(){
        setup:
        Map login= [ username: username, password: TEST_PASSWORD]
        def json = new JsonBuilder()
        json rootKey: login

        when:
        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(login)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("login"))

        and:
        jsonResult.username == username

        where:
        username << [TEST_USER_STUDENT, TEST_USER_EMPLOYER]
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def 'test loadProfile [student]'(){
        when:
        ResultActions response = mockMvc.perform(get("/profile")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("loadProfile"))

        and:
        jsonResult.username == TEST_USER_STUDENT
    }

    @WithMockCustomUser(username = TEST_USER_EMPLOYER)
    def 'test loadProfile [employer]'(){
        when:
        ResultActions response = mockMvc.perform(get("/profile")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("loadProfile"))

        and:
        jsonResult.username == TEST_USER_EMPLOYER
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test update profile action student"(){
        setup:
        Map params = requestStudent
        params.description = "NEW DESCRIPTION"
        params.studentId = "NEW ID"
        params.address =  [
                id: 1,
                street: "Calle Pere 24",
                city: "Cambrils",
                province: Province.TARRAGONA,
                zipcode: "43850",
                country: "España"
        ]
        def json = new JsonBuilder()
        json rootKey: params

        when:
        ResultActions response = mockMvc.perform(put("/profile/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())
        Student student = studentService.findByUsername(TEST_USER_STUDENT)

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("updateProfile"))

        and:
        jsonResult.message == "Se ha actualizado correctamente. "
        student.description == params.description
        student.studentId == params.studentId
    }

    @WithMockCustomUser(username = TEST_USER_EMPLOYER)
    def "test update profile action employer"(){
        setup:
        Map params = requestEmployer
        params.description = "NEW DESCRIPTION"
        params.address =  [
                id: 1,
                street: "Calle Pere 24",
                city: "Cambrils",
                province: Province.TARRAGONA,
                zipcode: "43850",
                country: "España"
        ]
        def json = new JsonBuilder()
        json rootKey: params

        when:
        ResultActions response = mockMvc.perform(put("/profile/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())
        Employer employer = employerService.findByUsername(TEST_USER_EMPLOYER)

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("updateProfile"))

        and:
        jsonResult.message == "Se ha actualizado correctamente. "
        employer.description == params.description
    }


    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test reset password "() {
        setup:
        setup:
        Map params= [ password: 'newpass', passwordConfirm: 'newpass']
        def json = new JsonBuilder()
        json rootKey: params


        when:
        ResultActions response = mockMvc.perform(put("/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(params)))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("resetPassword"))
        jsonResult.message.contains('S\'ha actualizado los la')

    }

    @Unroll
    @WithMockUser()
    def "test jobpositions action"() {
        when:
        ResultActions response = mockMvc.perform(get("/auth/jobpositions")
                .param("name", name))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("jobpositions"))

        and:
        jsonResult.size() == result
        println(result)

        where:
        name       || result
        "a"        || 11
        "Profesor" || 4
        "Pro"      || 5
        "Camarero" || 1
    }

    @Unroll
    @WithMockUser()
    def "test universities action"() {
        when:
        ResultActions response = mockMvc.perform(get("/auth/universities")
                .param("name", name))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("universities"))

        and:
        jsonResult.size() == result
        println(result)

        where:
        name          || result
        "Universidad" || 86
        "Oberta"      || 1
        "Barcelona"   || 2
    }

    @Unroll
    @WithMockUser()
    def "test areas action"() {
        when:
        ResultActions response = mockMvc.perform(get("/auth/areas")
                .param("name", name))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("areas"))

        and:
        jsonResult.size() == result
        println(result)

        where:
        name           || result
        "a"            || 17
        "Restauración" || 1
        "Danza"        || 1
    }

    @Unroll
    @WithMockUser()
    def "test provinces action"() {
        when:
        ResultActions response = mockMvc.perform(get("/auth/provinces")
                .param("name", name))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT
        def jsonResult = new JsonSlurper().parseText(response.andReturn().getResponse().getContentAsString())

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(AuthenticationController))
        response.andExpect(handler().methodName("provinces"))

        and:
        jsonResult.size() == result
        println(result)

        where:
        name        || result
        "Tarr"      || 1
        "Barcelona" || 1
    }
}
