package com.iquinto.workingstudent.controller;


import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/history")
public class HistoryController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value ="/list/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> listTotalsByYear(@PathVariable int year) {
        log.info("[c:history] [m:listTotalsByYear] starts ");

        User user  = userService.findByUsername(authenticationService.getCurrentUsername());
        if(user == null){
            String message = "El usuario no existe en el sistema";
            log.error("[c:history] [m:listTotalsByYear] username  " + user + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        List<Long> totals = historyService.listTotalsByYearAndMonths(user.getUsername(), year);
        log.info("[c:history] [m:listTotalsByYear] totals " + totals);
        return ResponseEntity.status(HttpStatus.OK).body(totals);
    }

}
