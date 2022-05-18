package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.JobPosition;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class JobPositionController {
    private static final Logger log = LoggerFactory.getLogger(JobPositionController.class);

    @Autowired
    JobPositionService jobPositionService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public ResponseEntity<?> list(){
        List<JobPosition> jobPositionList = jobPositionService.findAll();
        log.info("[c: category] [m:list] found : "  + jobPositionList.size());
        return ResponseEntity.status(HttpStatus.OK).body(jobPositionList);
    }

}
