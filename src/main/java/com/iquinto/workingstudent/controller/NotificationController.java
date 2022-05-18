package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Notification;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.payload.MessageResponse;
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
@RequestMapping("/notification")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value ="/list", method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestParam(required = false) Long all) {

        log.info("[c:notification] [m:list] starts " + all);
        User user  = userService.findByUsername(authenticationService.getCurrentUsername());

        List<Notification> list = null;
        if((all == 0)){
            list = notificationService.findAllByDestinationAndRead(user, false);
            log.info("[c:notification] [m:list] notifications unread " + list.size());
        } else {
            list =notificationService.findAllByDestination(user);
            log.info("[c:notification] [m:list] notifications all " + list.size());
        }


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }

    @RequestMapping(value ="/show/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Long id) {

        log.info("[c:notification] [m:show] starts ");
        Notification notification = notificationService.findById(id);
        if(notification == null){
            String message = "El aviso con id " + id + " no existe en el sistema";
            log.error("[c:notification] [m:show] notification with id  " + id + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }
        log.info("[c:notification] [m:show] notification is found "  + notification.toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notification);

    }

    @RequestMapping(value ="/markAsRead/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {

        log.info("[c:notification] [m:markAsRead] starts ");
        User user  = userService.findByUsername(authenticationService.getCurrentUsername());
        Notification notification = notificationService.findById(id);
        if(notification == null){
            String message = "El aviso con id " + id + " no existe en el sistema";
            log.error("[c:notification] [m:markAsRead] notification with id  " + id + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        notification.setRead(true);
        notificationService.save(notification);
        int count = notificationService.findAllByDestinationAndRead(user, false).size();
        log.info("[c:notification] [m:markAsRead] notification with id " + id + " marked as read!. Remaining unread "  + count);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(count);
    }

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("[c:notification] [m:delete] markAsRead ");
        Notification notification = notificationService.findById(id);
        if(notification == null){
            String message = "El aviso con id " + id + " no existe en el sistema";
            log.error("[c:notification] [m:delete] notification with id  " + id + " not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        String message = "";
        notificationService.delete(notification);
        message = "Se ha eliminado correctamente la notificacion.";
        log.info("[c:notification] [m:delete] deleted succesfully!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse(message));
    }

}
