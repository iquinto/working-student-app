package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.payload.JobPostRequest;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/job")
public class JobPostController {
    private static final Logger log = LoggerFactory.getLogger(JobPostController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ResumeStorageService resumeStorageService;


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value ="/list/{type}", method = RequestMethod.GET)
    public Page<JobPost> list(
            @PathVariable  String type,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) String employer,
            @RequestParam(required = false) String state,
            @RequestParam Optional<Integer> page ,
            @RequestParam Optional<Integer> size) {

        log.info("[c:job] [m:list] starts type:"+type+ ", query:"+query+
                ", category:"+category+ ", employer:"+employer+ ", minSalary:"
                +minSalary+ ", maxSalary:"+maxSalary + ", state:"+state  );

        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        LocalDate now = LocalDate.now();
        Page<JobPost> jobPosts = null;

        User user = userService.findByUsername(authenticationService.getCurrentUsername());

        if(employer == null){
            if(query != null && category == null){
                jobPosts = jobPostService.searchQueryBasicStudent(query, type, now, minSalary, maxSalary, pageable);
            } else if(query == null && category != null){
                Area c = areaService.findById(category);
               jobPosts = jobPostService.searchCategoryBasicStudent(c, type, now, minSalary, maxSalary, pageable);
            } else if(query != null && category != null){
                Area c = areaService.findById(category);
                jobPosts = jobPostService.searchCategoryQueryBasicStudent(c, query, type, now, minSalary, maxSalary, pageable);
            } else {
                jobPosts =  jobPostService.searchBasicStudent(type, now, minSalary, maxSalary, pageable);
            }
        } else {
            Employer e = employerService.findByUsername(employer);
            if(category != null && state == null){
                Area c = areaService.findById(category);
                jobPosts = jobPostService.searchCategoryBasicEmployer(c, e, type, pageable);
            } else if(category == null && state != null){
                if (state.equals("active")){
                    jobPosts = jobPostService.searchActiveBasicEmployer(now, e, type, pageable);
                } else if(state.equals("inactive")) {
                    jobPosts = jobPostService.searchNotActiveBasicEmployer(now, e, type, pageable);
                } else{
                    jobPosts = jobPostService.searchBasicEmployer(e, type, pageable);
                }
            }else if(category != null && state != null){
                Area c = areaService.findById(category);
                if (state.equals("active")){
                  jobPosts = jobPostService.searchCategoryActiveBasicEmployer(c, now, e, type, pageable);
                } else if(state.equals("inactive")) {
                   jobPosts = jobPostService.searchCategoryNotActiveBasicEmployer(c, now, e, type, pageable);
                }else{
                   jobPosts = jobPostService.searchCategoryBasicEmployer(c, e, type, pageable);
                }
            } else {
                jobPosts =  jobPostService.searchBasicEmployer(e, type, pageable);
            }
        }
        log.info("[c:job] [m:list] :"  + jobPosts.getTotalElements());

        return jobPosts;
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody JobPostRequest jobPostRequest) {

        log.info("[c: jobPost] [m:save] creating new jobPost ");
        Employer employer = employerService.findByUsername(authenticationService.getCurrentUsername());

        JobPost jobPost = new JobPost();
        jobPost.setType(jobPostRequest.getType());
        jobPost.setTitle(jobPostRequest.getTitle());
        jobPost.setRequirements(jobPostRequest.getRequirements());
        jobPost.setDescription(jobPostRequest.getDescription());
        jobPost.setStartDate(jobPostRequest.getStartDate());
        Area category = areaService.findById(jobPostRequest.getCategory());
        log.info("[c: jobPost] [m:save] category id: " + category.getId()  + " | name: " + category.getName());
        jobPost.setCategory(category);
        jobPost.setEmployer(employer);
        jobPost.setYearSalary(jobPostRequest.getYearSalary());
        jobPost.setExpiration(jobPostRequest.getExpiration());
        jobPostService.save(jobPost);
        log.info("[c: jobPost] [m:save] saved correctly id:" + jobPost.getId());

        return ResponseEntity.status(HttpStatus.OK).body(jobPost);
    }

    @RequestMapping(value ="/show/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show( @PathVariable Long id) {

        log.info("[c: jobPost] [m:show] find jobPost with id" + id);
        JobPost jobPost = jobPostService.get(id);
        if(jobPost == null) {
            log.info("[c: jobPost] [m:show]  jobPost not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No se encuentra en el sistema"));
        }
        log.info("[c: jobPost] [m:show]  jobPost is found : " + jobPost);

        return ResponseEntity.status(HttpStatus.OK).body(jobPost);
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobPostRequest jobPostRequest) {

        log.info("[c: jobPost] [m:update] updating  jobPost with id " + id);
        Employer employer = employerService.findByUsername(authenticationService.getCurrentUsername());
        JobPost jobPost = jobPostService.get(id);
        if(jobPost == null){
            log.error("[c: jobPost] [m:update] jobPost not found");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No se encuantra en el sistema"));
        }

        jobPost.setType(jobPostRequest.getType());
        jobPost.setTitle(jobPostRequest.getTitle());
        jobPost.setRequirements(jobPostRequest.getRequirements());
        jobPost.setDescription(jobPostRequest.getDescription());
        jobPost.setStartDate(jobPostRequest.getStartDate());
        Area category = areaService.findById(jobPostRequest.getCategory());
        log.info("[c: jobPost] [m:update] category id: " + category.getId()  + " | name: " + category.getName());
        jobPost.setCategory(category);
        jobPost.setEmployer(employer);
        jobPost.setYearSalary(jobPostRequest.getYearSalary());
        jobPost.setExpiration(jobPostRequest.getExpiration());
        JobPost persisted = jobPostService.save(jobPost);
        log.info("[c: jobPost] [m:update] jobPost updated correctly: " + persisted);
        return ResponseEntity.status(HttpStatus.OK).body(persisted);
    }

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete( @PathVariable Long id) {
        log.info("[c: jobPost] [m:delete] deleting jobpost with id :"  + id);
        Employer employer = employerService.findByUsername(authenticationService.getCurrentUsername());
        try{
            jobPostService.delete(id);
        }catch (Exception e){
            log.info("[c: jobPost] [m:delete] unable to delete :"  + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No es posible eliminar"));
        }

        return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado correctamente!");
    }

    @RequestMapping(value ="/findEmployer/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findEmployerByJobPost( @PathVariable Long id) {
        
        log.info("[c: jobPost] [m:findEmployerByJobPost] finding employer of jobPot id :"  + id);
        JobPost jobPost = jobPostService.get(id);
        Employer employer = employerService.findByUsername(jobPost.getEmployer().getUsername());
        log.info("[c: jobPost] [m:findEmployerByJobPost] employer is found :" + employer);

        return ResponseEntity.status(HttpStatus.OK).body(employer);
    }

    @RequestMapping(value ="/sendCV", method = RequestMethod.GET)
    public ResponseEntity<?> sendCV(@RequestParam Long idPost,@RequestParam String idResume) {

        JobPost jobPost = jobPostService.get(idPost);
        Resume resume = resumeStorageService.getFile(idResume);
        Employer employer = employerService.findByUsername(jobPost.getEmployer().getUsername());
        Student student = studentService.findByUsername(resume.getStudent().getUsername());
        boolean sent = emailService.sendMailWithAttachment(student,employer,jobPost,resume);

        log.info("[c: jobPost] [m:sendCV] email is sent :" + sent);
        Set<Student> students = new HashSet<>();
        students.add(student);

        if(sent){
            jobPost.setCandidates(students);
            jobPostService.save(jobPost);
            log.info("[c: jobPost] [m:sendCV] jobPost candidate size :" + jobPost.getCandidates().size());
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha enviado correctamente"));
        } else {
            log.info("[c: jobPost] [m:sendCV] email not sent " );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No es posible enviar el CV"));
        }
    }
    
    @RequestMapping(value ="/checkCandidate", method = RequestMethod.GET)
    public ResponseEntity<?> checkCandidate(@RequestParam Long idPost) {
        JobPost jobPost = jobPostService.get(idPost);
        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        boolean isCandidate = jobPost.getCandidates().contains(student);
        log.info("[c: jobPost] [m:checkCandidate] checkCandidate :" + isCandidate);
        return ResponseEntity.status(HttpStatus.OK).body(isCandidate);
    }
}
