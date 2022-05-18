package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
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

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JobPositionService jobPositionService;

    @RequestMapping(value ="/list", method = RequestMethod.GET)
    public Page<Student> list(
            @RequestParam(required = false) Long  jobPosition,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String day,
            @RequestParam(required = false) Optional<Double>  startTime,
            @RequestParam(required = false) Optional<Double>  endTime,
            @RequestParam Optional<Integer> page ,
            @RequestParam Optional<Integer> size) {

        log.info("[c:student] [m:list] jobPosition:" + jobPosition + ", city:" + city +  ", day:" + day + ", startTime:" + startTime + ", endTime:" + endTime);


        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));

        Page<Student> studentList = null;
        boolean reserve = false;

        if(jobPosition != null && city == null && day ==null){
            JobPosition c = jobPositionService.findById(jobPosition);
            studentList = studentService.searchCategoryStartEnd(c, startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        } else if (city != null && jobPosition == null) {
            studentList = studentService.searchCityStartEnd(city.toLowerCase(), startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        } else if (day != null && jobPosition == null) {
            DaysOfTheWeek daysOfTheWeek = DaysOfTheWeek.fromString(day);
            studentList = studentService.searchDayStartEnd(daysOfTheWeek, startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        }else if (day != null && jobPosition != null && city == null) {
            DaysOfTheWeek daysOfTheWeek = DaysOfTheWeek.fromString(day);
            JobPosition c = jobPositionService.findById(jobPosition);
            studentList = studentService.searchDayCategoryStartEnd(daysOfTheWeek, c, startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        }else if (day == null && jobPosition != null && city != null) {
            JobPosition c = jobPositionService.findById(jobPosition);
            studentList = studentService.searchCategoryCityStartEnd(c, city.toLowerCase(), startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        }else if (day != null && jobPosition != null && city != null) {
            JobPosition c = jobPositionService.findById(jobPosition);
            DaysOfTheWeek daysOfTheWeek = DaysOfTheWeek.fromString(day);
            studentList = studentService.searchDayCategoryCityStartEnd(daysOfTheWeek, c, city.toLowerCase(), startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        } else {
            studentList = studentService.searchStartEnd(startTime.orElse(0.0), endTime.orElse(24.0 ), pageable);
        }

        log.info("[m:list] totalElements :"  + studentList.getTotalElements());

        return studentList;
    }


    @RequestMapping(value ="/list/contracted", method = RequestMethod.GET)
    public Page<Student> listContractred(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        log.info("[m:listContractred] page " + page );

        Employer employer = employerService.findByUsername(authenticationService.getCurrentUsername());
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(3));

        Page<Student> studentList= reservationService.findAllStudentsInReservationByEmployer(employer, pageable);


        log.info("[m:listContractred] students found total elements: "
                + studentList.getTotalElements() + "total pages " + studentList);

        return studentList;
    }

    @RequestMapping(value ="/show/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable String username) {
        log.info("[m:show] searching for " + username);
        Student student = studentService.findByUsername(username);
        if(student == null){
            String message = "El usuario  no existe en el sistema";
            log.error("[m:show] username  " + username + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }


}
