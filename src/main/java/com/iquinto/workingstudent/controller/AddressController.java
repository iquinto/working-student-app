package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Address;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/address")
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Address address){
        Address ad = addressService.save(address);
        log.info("[controller: address] [m:save] saving new address " + ad);
        return ResponseEntity.status(HttpStatus.OK).body(ad);
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public ResponseEntity<?> delete(@PathVariable Long id){
        Address address = addressService.get(id);
        addressService.delete(address);
        log.info("[controller: delete] [m:save] deleted" );
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.DELETE_CORRECTLY));
    }
}
