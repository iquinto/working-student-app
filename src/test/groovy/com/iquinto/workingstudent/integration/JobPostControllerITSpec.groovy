package com.iquinto.workingstudent.integration

import com.iquinto.workingstudent.WorkingStudentApplication
import com.iquinto.workingstudent.controller.JobPostController
import com.iquinto.workingstudent.model.JobPost
import com.iquinto.workingstudent.model.Resume
import com.iquinto.workingstudent.model.Student
import com.iquinto.workingstudent.service.AreaService
import com.iquinto.workingstudent.service.JobPostService
import com.iquinto.workingstudent.service.ResumeStorageService
import com.iquinto.workingstudent.utils.SharedConfigSpecification
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Ignore
import spock.lang.Stepwise
import spock.lang.Unroll

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
class JobPostControllerITSpec extends SharedConfigSpecification {

    @Autowired AreaService areaService


    @WithMockUser
    def "test register list  student 1"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('query', 'a')
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list  student 2"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('category', '1')
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list  student 3"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('query', 'a')
                .param('category', '1')
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list  student 4"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list  employer category is not null"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('category', '1')
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('employer',TEST_USER_EMPLOYER)
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }

    @WithMockUser
    def "test register list  employer where category is  null and state is not null"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('state', state)
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('employer',TEST_USER_EMPLOYER)
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))

        where:
        state << ['active', 'inactive' , 'xxx']
    }

    @WithMockUser
    def "test register list  employer where category is not null and state is not null"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('state', state)
                .param('category', '1')
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('employer',TEST_USER_EMPLOYER)
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))

        where:
        state << ['active', 'inactive' , 'xxx']
    }

    @WithMockUser
    def "test register list  employer category and state are null"(){
        when:
        ResultActions response = mockMvc.perform(get("/job/list/temporary")
                .param('minSalary','5000')
                .param('maxSalary','50000')
                .param('employer',TEST_USER_EMPLOYER)
                .param('page', '1')
                .param('size', '1')
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("list"))
    }


    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test save"(){
        given:
        Map map = [:]
        map.type="temporary"
        map.title="Some title"
        map.requirements="Some title"
        map.description="Some title"
        map.startDate=LocalDate.now().plusDays(10)
        map.category= 1
        map.yearSalary=20000
        map.expiration= LocalDate.now().plusDays(20)
        def json = new JsonBuilder()
        json rootKey: map

        when:
        ResultActions response = mockMvc.perform(post("/job/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(map))
        )

        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("save"))
    }

    @Unroll
    @WithMockUser
    def "test show"() {
        when:
        ResultActions response = mockMvc.perform(get("/job/show/${id}")
        )
        response.andDo(MockMvcResultHandlers.print()) // PRINT RESULT

        then:
        found ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("show"))

        where:
        id || found
        1  || true
        55 || false
    }

    @Unroll
    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test  update"() {
        given:
        Map map = [:]
        map.type="temporary"
        map.title="Some title"
        map.requirements="Some title"
        map.description="Some title"
        map.startDate=LocalDate.now().plusDays(10)
        map.category= 1
        map.yearSalary=20000
        map.expiration= LocalDate.now().plusDays(20)
        def json = new JsonBuilder()
        json rootKey: map

        when:
        ResultActions response = mockMvc.perform(put("/job/update/${id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AS_JSON_STRING(map))
        )

        then:
        found ? response.andExpect(status().isOk()) : response.andExpect(status().isBadRequest())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("update"))

        where:
        id || found
        2  || true
        55 || false
    }


    @Autowired JobPostService jobPostService
    @Unroll
    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test delete"() {
        given:
        JobPost jobPost = jobPostService.findAll().last()

        when:
        ResultActions response = mockMvc.perform(delete("/job/delete/${jobPost.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("delete"))
    }
    @Autowired ResumeStorageService resumeStorageService

    @WithMockUser(username = TEST_USER_EMPLOYER)
    def "test send CV"() {
        given:
        Resume resume = new Resume("testcv", "application/pdf", "some bytes".getBytes());
        resume.setAlias("test");
        resume.setDescription("test");
        resume.setStudent(studentService.findByUsername(TEST_USER_STUDENT));
        resume = resumeStorageService.save(resume);

        when:
        ResultActions response = mockMvc.perform(get("/job/sendCV")
                .param('idPost', '1')
                .param('idResume', resume.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("sendCV"))
    }

    @WithMockUser(username = TEST_USER_STUDENT)
    def "test checkCandidate"() {
        when:
        ResultActions response = mockMvc.perform(get("/job/checkCandidate")
                .param('idPost', '1')
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        response.andExpect(status().isOk())
        response.andExpect(handler().handlerType(JobPostController))
        response.andExpect(handler().methodName("checkCandidate"))
    }

}
