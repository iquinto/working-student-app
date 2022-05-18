package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Rating;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.RatingRequest;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rating")
public class RatingController {
    private static final Logger log = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    RatingService ratingService;

    @Autowired
    StudentService studentService;


    @RequestMapping(value = {"/average/{username}"}, method = RequestMethod.GET)
    public ResponseEntity<?> average(@PathVariable String username){
        
        log.info("[c:rating] [m:average] average starts "  + username);
        Student student = studentService.findByUsername(username);
        if(student == null){
            String message = "El usuario no existe en el sistema.";
            log.error("[c:rating] [m:average] username  " + student + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }
        Double total = ratingService.getAverage(student);
        log.info("[c:rating] [m:average] average " + total);
        return ResponseEntity.status(HttpStatus.OK).body(total != null ? total : 0.0) ;
    }


    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody RatingRequest ratingRequest){
        log.info("[c:rating] [m:save] starts ");

        Student student = studentService.findByUsername(ratingRequest.getUsername());
        if(student == null){
            String message = "El usuario no existe en el sistema";
            log.error("[c:rating] [m:average] username  " + student + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        Rating rating = new Rating(ratingRequest.getReservationId(), ratingRequest.getRate(),  student);
        rating.setComment(ratingRequest.getComment());

        Rating persited = ratingService.save(rating);
        log.info("[c:rating] [m:save] saved sucessfully" + persited.toString());

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.SAVE_CORRECTLY));
    }


}
