package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.service.AuthenticationService;
import com.iquinto.workingstudent.service.EmployerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employer")
public class EmployerController {
    private static final Logger log = LoggerFactory.getLogger(EmployerController.class);

    @Autowired
    private EmployerService employerService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value ="/show/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable String username) {
        Employer employer = employerService.findByUsername(username);

        if(employer == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No se ha encontrado el usuario  en el sistema"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(employerService.findByUsername(username));
    }
}
