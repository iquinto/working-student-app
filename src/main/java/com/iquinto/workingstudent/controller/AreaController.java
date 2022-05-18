package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Area;
import com.iquinto.workingstudent.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/area")
public class AreaController {
    private static final Logger log = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public ResponseEntity<?> list(){
        List<Area> areaList = areaService.findAll();
        log.info("[c: category] [m:list] found : "  + areaList.size());
        return ResponseEntity.status(HttpStatus.OK).body(areaList);
    }

}
