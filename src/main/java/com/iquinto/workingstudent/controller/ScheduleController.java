package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.ScheduleRequest;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    StudentService studentService;

    @Autowired
    SlotService slotService;


    @RequestMapping(value = {"/slotsByDay"}, method = RequestMethod.GET)
    public ResponseEntity<?> listSlotsByDay(){
        return ResponseEntity.status(HttpStatus.OK).body(slotService.getAllSchedulesOrderByDay());
    }


    @RequestMapping(value = {"/slotsByStartTime"}, method = RequestMethod.GET)
    public ResponseEntity<?> slotsByStartTime(){
        return ResponseEntity.status(HttpStatus.OK).body(slotService.getAllSchedulesOrderByStart());
    }

    @RequestMapping(value = {"/slots"}, method = RequestMethod.GET)
    public ResponseEntity<?> listSlots(){
        return ResponseEntity.status(HttpStatus.OK).body(slotService.findAll());
    }
    

    @RequestMapping(value = {"/list/{username}"}, method = RequestMethod.GET)
    public ResponseEntity<?> listSchedules(@PathVariable String username){

        Student student = studentService.findByUsername(username);
        if(student == null){
            log.error("[c: schedule] [m:saveSchedules] user not found " );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No existe es usuario " + username+" en el sistema"));
        }
        log.info("[c: schedule] [m:saveSchedules] user is found: " + student );
        List<Schedule> scheduleList = scheduleService.findAllByStudent(student);

        log.info("[c: schedule] [m:saveSchedules] listSchedules: " + scheduleList.size() );
        return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
    }



    @RequestMapping(value = {"/saveAll/{username}"}, method = RequestMethod.POST)
    public ResponseEntity<?> saveSchedules(@PathVariable String username, @RequestBody ScheduleRequest scheduleRequest){

        Student student = studentService.findByUsername(username);

        if(student == null){
            log.error("[c: schedule] [m:saveSchedules] user not found " );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No existe es usuario " + username+" en el sistema"));
        }
        log.info("[c: schedule] [m:saveSchedules] user is found: " + student );

        List<Schedule> scheduleList =  new ArrayList<>();

        try{
            // remove all schedules except reserved
            scheduleService.deleteByReserveAndStudent(false, student);

            for (Long slotId: scheduleRequest.getSlots()){
                Schedule s = new Schedule();
                Slot slot = slotService.findById(slotId);
                if(!scheduleService.existsBySlotAndReserveAndStudent(slot,true, student)){
                    s.setSlot(slot);
                    s.setStudent(student);
                    s.setReserve(false);
                    scheduleList.add(scheduleService.save(s));
                }
            }

        }catch (Exception e){
            log.error("[c: schedule] [m:saveSchedules]  error " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al procesar los datos. " + e.getMessage());
        }
        log.info("[c: schedule] [m:saveSchedules] schedules: " + scheduleList.size());

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha guardado correctamente los datos!"));
    }

    @RequestMapping(value = {"/get/username/{username}/slot/{slot}"}, method = RequestMethod.GET)
    public ResponseEntity<?> getScheduleByUserNameAndSlot(@PathVariable String username, @PathVariable Long slot){

        Student student = studentService.findByUsername(username);

        if(student == null){
            log.error("[c: schedule] [m:getScheduleByUserNameAndSlot] user not found " );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No existe es usuario " + username+" en el sistema"));
        }

        log.info("[c: getScheduleByUserNameAndSlot] [m:saveSchedules] user is found: " + student );

        Slot s = slotService.findById(slot);
        Schedule schedule = scheduleService.findByStudentdAndSlot(student, s);

        log.info("[c: schedule] [m:getScheduleByUserNameAndSlot] schedule is found: " + schedule);

        return ResponseEntity.status(HttpStatus.OK).body(schedule);
    }

}
