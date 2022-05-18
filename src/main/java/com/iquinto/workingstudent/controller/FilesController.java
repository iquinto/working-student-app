package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Photo;
import com.iquinto.workingstudent.model.Resume;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/files")
public class FilesController {
    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @Value("${iquinto.app.filePath}")
    private String filePath;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private ResumeStorageService resumeStorageService;

    @Autowired
    AuthenticationService authenticationService;


    @RequestMapping(value ="/photo/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAvatar(@PathVariable String id) {
        log.info("[c: authentication] [m:getPublicImage] file id : " + id );
        Photo photo = photoStorageService.getFile(id);
        log.info("[c: authentication] [m:getFileByUser] file found: " + photo.toString());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
                .body(photo.getData());
    }

    @RequestMapping(value ="/resume/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCV(@PathVariable String id) {
        log.info("[c: authentication] [m:getPublicImage] file id : " + id );
        Resume resume = resumeStorageService.getFile(id);
        log.info("[c: authentication] [m:getFileByUser] file found: " + resume.toString());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getName() + "\"")
                .body(resume.getData());
    }
}
