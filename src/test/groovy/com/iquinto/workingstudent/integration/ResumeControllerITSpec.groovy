package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.ResumeController
import com.iquinto.workingstudent.model.Resume
import com.iquinto.workingstudent.model.User
import com.iquinto.workingstudent.service.PhotoStorageService
import com.iquinto.workingstudent.service.ResumeStorageService
import com.iquinto.workingstudent.service.StudentService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import com.iquinto.workingstudent.utils.WithMockCustomUser
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResumeControllerITSpec extends SharedConfigSpecification {

    private final static String BASE_URL = "/resume"

    @Autowired ResumeStorageService resumeStorageService
    Resume persited

    @WithMockUser(username = TEST_USER_STUDENT)
    def "test list action correctly"() {
        when:
        ResultActions response = mockMvc.perform(get("${BASE_URL}/list")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ResumeController))
        response.andExpect(handler().methodName("list"))
        response.andExpect(status().isOk())
    }

    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test save action"(){
        setup:
        MockMultipartFile resume  = new MockMultipartFile(
                "resume",
                "avatar.png",
                "img/png",
                "Some photo".getBytes()
        );

        when: 'upload avatar'
        ResultActions response = mockMvc.perform(multipart(BASE_URL + "/save")
                .file(resume)
                .header("Authorization", "BearereyJhbGciOiJIUzUxMiJ9")
                .param('alias', 'some alias')
                .param('description', 'some description')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ResumeController))
        response.andExpect(handler().methodName("save"))
        response.andExpect(status().isOk())
    }

    @WithMockUser(username = TEST_USER_STUDENT)
    def "test show correctly"() {
        given:
        Resume resume = new Resume("testcv", "application/pdf", "some bytes".getBytes());
        resume.setAlias("test");
        resume.setDescription("test");
        resume.setStudent(studentService.findByUsername(TEST_USER_STUDENT));
        persited = resumeStorageService.save(resume);

        when:
        ResultActions response = mockMvc.perform(get("${BASE_URL}/show/" + persited.getId())
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ResumeController))
        response.andExpect(handler().methodName("show"))
        response.andExpect(status().isOk())
    }

/*
    @WithMockCustomUser(username = TEST_USER_STUDENT)
    def "test update action"(){
        setup:
        given:
        Resume r = new Resume("testcv", "application/pdf", "some bytes".getBytes());
        r.setAlias("test");
        r.setDescription("test");
        r.setStudent(studentService.findByUsername(TEST_USER_STUDENT));
        persited = resumeStorageService.save(r);

        MockMultipartFile resume  = new MockMultipartFile(
                "resume",
                "avatar.png",
                "img/png",
                "Some photo".getBytes()
        );

        when:
        ResultActions response = mockMvc.perform(put(BASE_URL + "/update/${persited.getId()}")
                .param('resume', resume)
                .header("Authorization", "BearereyJhbGciOiJIUzUxMiJ9")
                .param('alias', 'some alias edited')
                .param('description', 'some description edit')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ResumeController))
        response.andExpect(handler().methodName("update"))
        response.andExpect(status().isOk())
    }
*/
    @WithMockUser(username = TEST_USER_STUDENT)
    def "test delete action correctly"() {
        given:
        Resume resume = new Resume("testcv", "application/pdf", "some bytes".getBytes());
        resume.setAlias("test");
        resume.setDescription("test");
        resume.setStudent(studentService.findByUsername(TEST_USER_STUDENT));
        persited = resumeStorageService.save(resume);

        when:
        ResultActions response = mockMvc.perform(delete("${BASE_URL}/delete/${persited.id}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(ResumeController))
        response.andExpect(handler().methodName("delete"))
        response.andExpect(status().isOk())
    }

}
