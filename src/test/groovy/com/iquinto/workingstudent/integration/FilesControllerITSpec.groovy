package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.EmployerController
import com.iquinto.workingstudent.controller.FilesController
import com.iquinto.workingstudent.model.Photo
import com.iquinto.workingstudent.model.Resume
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.service.PhotoStorageService
import com.iquinto.workingstudent.service.ResumeStorageService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import spock.lang.Stepwise
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Stepwise
@ActiveProfiles("test")
@SpringBootTest(classes = WorkingStudentApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilesControllerITSpec extends SharedConfigSpecification {

    @Autowired
    PhotoStorageService photoStorageService

    @Autowired
    ResumeStorageService resumeStorageService

    @Transactional
    @WithMockUser
    def "test getAvatar"() {
        given:
        Student student = studentService.findByUsername(TEST_USER_STUDENT);
        Photo f = photoStorageService.storeWithFile('avatar', 'image/png', 'aaa'.getBytes());
        student.setPhoto(f);
        studentService.save(student)

        when:
        ResultActions response = mockMvc.perform(get("/files/photo/${f.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(FilesController))
        response.andExpect(handler().methodName("getAvatar"))
        response.andExpect(status().isOk())
    }

    @WithMockUser
    def "test getCV"() {
        given:
        Student student = studentService.findByUsername(TEST_USER_STUDENT);


        Resume resume = new Resume("testcv", "application/pdf", "some bytes".getBytes());
        resume.setAlias("test");
        resume.setDescription("test");
        resume.setStudent(student);
        Resume persited = resumeStorageService.save(resume);


        when:
        ResultActions response = mockMvc.perform(get("/files/resume/${persited.getId()}")
                .contentType(MediaType.APPLICATION_JSON))
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(handler().handlerType(FilesController))
        response.andExpect(handler().methodName("getCV"))
        response.andExpect(status().isOk())
    }

}
