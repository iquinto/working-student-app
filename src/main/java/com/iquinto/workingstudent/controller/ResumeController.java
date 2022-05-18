package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Resume;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.ResumeResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/resume")
public class ResumeController {
    private static final Logger log = LoggerFactory.getLogger(ResumeController.class);

    @Value("${iquinto.app.filePath}")
    private String filePath;

    @Autowired
    private ResumeStorageService resumeStorageService;

    @Autowired
    private StudentService studentService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value ="/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        log.info("[c: resume] [m:list] find resume starts");
        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        List<ResumeResponse> resumeResponses = resumeStorageService.findAllByStudent(student).map(i -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path(filePath + "resume/")
                    .path(i.getId())
                    .toUriString();

            return new ResumeResponse(
                    i.getId(),
                    i.getName(),
                    i.getAlias(),
                    i.getDescription(),
                    fileDownloadUri,
                    i.getType(),
                    i.getData().length);
        }).collect(Collectors.toList());
        log.info("[c: resume] [m:list] found  : " + resumeResponses.size() );

        return ResponseEntity.status(HttpStatus.OK).body(resumeResponses);
    }

    @RequestMapping(value ="/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestParam("resume") MultipartFile file,
                                  @RequestParam(required = false) String alias,
                                  @RequestParam(required = false) String description  ) {

        
        log.info("[c: resume] [m:save] start : alias:" + alias + " desciption:" + description + " file:" + file );
        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        log.info("[c: resume] [m:save] uploading file for : " + student.toString() );
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Resume resume = new Resume(fileName, file.getContentType(), file.getBytes());
            resume.setAlias(alias);
            resume.setDescription(description);
            resume.setStudent(student);
            Resume persited = resumeStorageService.save(resume);

            student.addResume(persited);
            studentService.save(student);
            log.info("[c: resume] [m:save] save succesfully : " + persited.toString() );
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.SAVE_CORRECTLY));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("No es posible guardar " + file.getOriginalFilename() + "!"));
        }
    }

    @RequestMapping(value ="/show/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable String id) {
        log.info("[c: resume] [m:show] starts "  );

        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        Resume dbFile = resumeStorageService.getFile(id);
        log.info("[c: resume] [m:show] resume found!" + dbFile.toString());

        String fileDownloadUri  = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(filePath + "resume/")
                .path(dbFile.getId())
                .toUriString();
        ResumeResponse resumeResponse = new ResumeResponse(
                dbFile.getId(),
                dbFile.getName(),
                dbFile.getAlias(),
                dbFile.getDescription(),
                fileDownloadUri,
                dbFile.getType(),
                dbFile.getData().length);
        return ResponseEntity.status(HttpStatus.OK).body(resumeResponse);
    }

    /*
    @RequestMapping(value ="/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String id, @RequestParam("resumes") MultipartFile file,
                                    @RequestParam(required = false) String alias,
                                    @RequestParam(required = false) String description  ) {

        log.info("[c: resume] [m:save: alias:" + alias + " desciption:" + description + " file:" + file );

        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        String message = "";
        log.info("[c: resume] [m:update] uploading file for : " + student.toString() );
        Resume resume = resumeStorageService.getFile(id);
        log.info("[c: resume] [m:update] found : " + resume.toString() );
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            resume.setName(fileName);
            resume.setType(file.getContentType());
            resume.setData(file.getBytes());
            resume.setAlias(alias);
            resume.setDescription(description);
            resume.setStudent(student);
            resumeStorageService.save(resume);
            log.info("[c: resume] [m:update] save succesfully : " + resume.toString() );

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.UPDATE_CORRECTLY));

        } catch (Exception e) {
            message = "No es posible guardar " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }
    */

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        Student student = studentService.findByUsername(authenticationService.getCurrentUsername());
        Resume dbFile = resumeStorageService.getFile(id);
        log.info("[c: resume] [m:show] resume found!" + dbFile.toString());
        try{
            resumeStorageService.delete(dbFile);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(MessageResponse.DELETE_FAILED));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.DELETE_CORRECTLY));
    }

}
