package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.Role;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.ReservationRequest;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    EmployerService employerService;

    @Autowired
    SlotService slotService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    RatingService ratingService;

    @Autowired
    HistoryService historyService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ScheduleService scheduleService;


    @Autowired
    EmailService emailService;

    @RequestMapping(value = {"/list/{username}"}, method = RequestMethod.GET)
    public Page<Reservation> list(@PathVariable String username,
                                  @RequestParam Optional<Integer> page ,
                                  @RequestParam Optional<Integer> size){

        User user = userService.findByUsername(username);
        boolean isEmployer = user.getRole() == Role.ROLE_EMPLOYER;
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.by("endDate").ascending());
        Page<Reservation> reservations = isEmployer ? reservationService.findAllByEmployer((Employer) user, pageable) :
                reservationService.findAllByStudent((Student)user, pageable) ;
        log.info("[c: reservation] [m:list] list found: " + reservations );
        
        return reservations;
    }

    @RequestMapping(value = {"/save/{username}"}, method = RequestMethod.POST)
    public ResponseEntity<?> saveReservation(@PathVariable String username, @RequestBody ReservationRequest reservationRequest){

        Employer employer = employerService.findByUsername(username);
        Student student = studentService.findByUsername(reservationRequest.getStudent());

        if(employer == null){
            log.error("[c: reservation] [m:save] user not found " );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("No existe es usuario " + username+" en el sistema"));
        }
        log.info("[c: reservation] [m:save] employer is found: " + employer );
        log.info("[c: reservation] [m:save] student is found: " + student );

        Set<Schedule> schedules = new HashSet<>();

        try{
            reservationRequest.getSchedules().stream().forEach((id)-> {
                log.info("[c: reservation] [m:save] HALA 1 " + id );

                Slot s = slotService.findById(id);
                log.info("[c: reservation] [m:save] HALA 2 " + s );

                Schedule schedule = scheduleService.findByStudentdAndSlot(student,s );

                log.info("[c: reservation] [m:save] HALA 3 " + schedule );
                schedule.setReserve(true);
                scheduleService.save(schedule);
                schedules.add(schedule);
            });

        }catch (Exception e){
            log.error("[c: reservation] [m:save]  error " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al procesar los datos. " + e.getMessage());
        }

        Reservation r = new Reservation(employer, reservationRequest.getStartDate(),  reservationRequest.getNumberOfWeeks());
        r.setSchedules(schedules);
        r.setStudent(student);
        r = reservationService.save(r);

        // create notifcation
        Set<Slot> slots = new HashSet<>();
        for (Schedule schedule : r.getSchedules()){
            slots.add(schedule.getSlot());
        }

        String msg = "reservation is created";
        Notification notification = new Notification(r.getEmployer(), r.getStudent(),  Notification.SAVE, msg);
        notificationService.save(notification);
        notification.setSlots(slots);
        notificationService.save(notification);
        emailService.sendNotificationByEmail(notification);

        log.info("[c: reservation] [m:save] reservation saved: " + r);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha guardado correctamente los datos!"));
    }

    @RequestMapping(value = {"/get/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){
        Employer employer = employerService.findByUsername(authenticationService.getCurrentUsername());
        Map reservation = reservationService.findByIdMap(id);
        log.info("[c: reservation] [m:getById] found: " + reservation);
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }


    @RequestMapping(value = {"/edit/id/{id}/user/{username}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateReservation(@PathVariable Long id,@PathVariable String username, @RequestBody ReservationRequest reservationRequest){

        Employer employer = employerService.findByUsername(username);
        log.info("[c: reservation] [m:updateReservation] employer is found: " + employer );

        Student student = studentService.findByUsername(reservationRequest.getStudent());
        log.info("[c: reservation] [m:updateReservation] student is found: " + student );

        Reservation r = reservationService.findById(id);
        log.info("[c: reservation] [m:updateReservation] reservation is found: " + r );

        r.getSchedules().stream().forEach((it)-> {
            Schedule schedule = scheduleService.findById(it.getId());
            schedule.setReserve(false);
            scheduleService.save(schedule);
        });

        Set<Schedule> schedules = new HashSet<>();
        try{
            reservationRequest.getSchedules().stream().forEach((it)-> {
                Schedule schedule = scheduleService.findByStudentdAndSlot(student, slotService.findById(it));
                schedule.setReserve(true);
                scheduleService.save(schedule);
                schedules.add(schedule);
            });

        }catch (Exception e){
            log.error("[c: reservation] [m:save]  error " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al procesar los datos. " + e.getMessage());
        }

        r.setStartDate(reservationRequest.getStartDate());
        r.setNumberOfWeeks(reservationRequest.getNumberOfWeeks());
        r.setSchedules(schedules);

        r = reservationService.save(r);

        // create notifcation
        Set<Slot> slots = new HashSet<>();
        for (Schedule schedule : r.getSchedules()){
            slots.add(schedule.getSlot());
        }

        String msg = "reservation is updated";
        Notification notification = new Notification(r.getEmployer(), r.getStudent(),  Notification.EDIT, msg);
        notification.setSlots(slots);
        notificationService.save(notification);
        emailService.sendNotificationByEmail(notification);

        log.info("[c: reservation] [m:updateReservation] reservation: " + reservationService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha guardado correctamente los datos!"));
    }

//http://localhost:8081/api/reservation/delete/id/4/user/test_employer
    @RequestMapping(value = {"/delete/id/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReservation(@PathVariable Long id,
                                               @RequestParam int rate,
                                               @RequestParam(required = false) String comment){

        log.info("[c: reservation] [m:deleteReservation] starts:  id=" + id + "  rate="+ rate + "  comment="+ comment);
        User user = userService.findByUsername(authenticationService.getCurrentUsername());
        log.info("[c: reservation] [m:deleteReservation] user is found: " + user );
        Reservation r = reservationService.findById(id);
        try{
            r.getSchedules().stream().forEach((it)-> {
                Schedule schedule = scheduleService.findById(it.getId());
                    log.info("[c: reservation] [m:deleteReservation] adding to  reservation scheduleId: " + it);
                    schedule.setReserve(false);
                    scheduleService.save(schedule);
            });

            // create rating
            if(rate > 0){
                Rating ra = new Rating(r.getId(), rate,  r.getStudent());
                if (!comment.isEmpty()){
                    ra.setComment(comment);
                }
                ratingService.save(ra);
            }

            // create notifcation
            Set<Slot> slots = new HashSet<>();
            for (Schedule schedule : r.getSchedules()){
                slots.add(schedule.getSlot());
            }

            String msg = "reservation is deleted";
            Notification notification = new Notification(r.getEmployer(), r.getStudent(),  Notification.DELETE, msg);
            notification.setSlots(slots);
            notificationService.save(notification);
            emailService.sendNotificationByEmail(notification);

            // create history
            History history = new History(r.getStudent().getUsername(), r.getId(), r.getEndDate().getMonthValue(), r.getEndDate().getYear(), LocalDate.now());
            historyService.save(history);

            reservationService.delete(r);


        }catch (Exception e){
            log.error("[c: reservation] [m:save]  error " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al procesar los datos. " + e.getMessage());
        }

        log.info("[c: reservation] [m:deleteReservation] reservation: ");

       return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha eliminado correctamente los datos!"));

    }

    @RequestMapping(value = {"/accept/id/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<?> acceptReservation(@PathVariable Long id){

        User user = userService.findByUsername(authenticationService.getCurrentUsername());
        log.info("[c: reservation] [m:acceptReservation] starts...");
        Reservation r = reservationService.findById(id);
        r.setAccepted(true);
        reservationService.save(r);

        // create notifcation
        Set<Slot> slots = new HashSet<>();
        for (Schedule schedule : r.getSchedules()){
            slots.add(schedule.getSlot());
        }

        String msg = "reservation is accepted";
        Notification notification = new Notification(r.getStudent(), r.getEmployer(), Notification.ACCEPTED, msg);
        notification.setSlots(slots);
        notificationService.save(notification);
        emailService.sendNotificationByEmail(notification);
        log.info("[c: reservation] [m:acceptReservation] reservation: ");

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha acceptado correctamente los datos!"));
    }


    @RequestMapping(value = {"/reject/id/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> rejectReservation(@PathVariable Long id){

        log.info("[c: reservation] [m:rejectReservation] starts...");
        User user = userService.findByUsername(authenticationService.getCurrentUsername());
        Reservation r = reservationService.findById(id);

        try{
            r.getSchedules().stream().forEach((it)-> {
                Schedule schedule = scheduleService.findById(it.getId());
                log.info("[c: reservation] [m:rejectReservation] adding to  reservation scheduleId: " + it);
                schedule.setReserve(false);
                scheduleService.save(schedule);
            });


            // create notifcation
            Set<Slot> slots = new HashSet<>();
            for (Schedule schedule : r.getSchedules()){
                slots.add(schedule.getSlot());
            }

            String msg = "reservation is rejected";
            Notification notification = new Notification(r.getStudent(), r.getEmployer(),  Notification.REJECTED, msg);
            notification.setSlots(slots);
            notificationService.save(notification);
            emailService.sendNotificationByEmail(notification);

            reservationService.delete(r);

        }catch (Exception e){
            log.error("[c: reservation] [m:save]  error " + e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error al procesar los datos. " + e.getMessage());
        }

        log.info("[c: reservation] [m:rejectReservation] reservation: ");

        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Se ha rechazado correctamente los datos!"));

    }
}
