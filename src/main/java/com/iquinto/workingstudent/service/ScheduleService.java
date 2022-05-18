package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.State;
import com.iquinto.workingstudent.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SlotService slotService;
    public Schedule findById(Long id){
       return scheduleRepository.getById(id);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findAllByStudent(Student student) {
        return scheduleRepository.findAllByStudent(student);
    }

    public Schedule save(Schedule schedule) {
        Schedule s = scheduleRepository.save(schedule);
        return s;
    }

    public void deleteByReserveAndStudent(boolean reserve, Student student) {
        scheduleRepository.deleteByReserveAndStudent(reserve, student);
    }

    public Boolean existsBySlotAndReserveAndStudent(Slot slot, boolean reserve, Student student) {
     return scheduleRepository.existsBySlotAndReserveAndStudent(slot, reserve,  student);
    }

    public Schedule findByStudentdAndSlot(Student student , Slot slot) {
        return scheduleRepository.findScheduleByStudentAndSlot(student, slot).get();
    }

    public void deleteAll(){
        scheduleRepository.deleteAll();
    }

}
