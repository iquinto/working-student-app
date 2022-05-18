package com.iquinto.workingstudent.service;
import com.iquinto.workingstudent.model.Slot;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
import com.iquinto.workingstudent.repository.SlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class SlotService {

    @Autowired
    SlotRepository slotRepository;

    public Slot findById(Long id){
        return slotRepository.getById(id);
    }

    public Slot save(Slot slot) {
        return  slotRepository.save(slot);
    }

    public List<Slot> findAll() {
       return slotRepository.findAll();
    }

    public List<Slot> findAllByDay(DaysOfTheWeek daysOfTheWeek) {
        return slotRepository.findAllByDay(daysOfTheWeek);
    }

    public Slot findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek day, Double startTime, Double endTime){
        return slotRepository.findByDayAndStAndStartTimeAndEndTime(day, startTime, endTime);
    };

    public List<Slot> findAllByStartTimeGreaterThan(Double startTime){
        return slotRepository.findAllByStartTimeGreaterThan(startTime);
    };

    public LinkedHashMap<String,Object> getAllSchedulesOrderByDay(){
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap();
        hashMap.put("lunes", slotRepository.findAllByDay(DaysOfTheWeek.LUNES));
        hashMap.put("martes", slotRepository.findAllByDay(DaysOfTheWeek.MARTES));
        hashMap.put("miercoles", slotRepository.findAllByDay(DaysOfTheWeek.MIERCOLES));
        hashMap.put("jueves", slotRepository.findAllByDay(DaysOfTheWeek.JUEVES));
        hashMap.put("viernes", slotRepository.findAllByDay(DaysOfTheWeek.VIERNES));
        hashMap.put("sabado", slotRepository.findAllByDay(DaysOfTheWeek.SABADO));
        hashMap.put("domingo", slotRepository.findAllByDay(DaysOfTheWeek.DOMINGO));
        return hashMap;
    }

    public LinkedHashMap<String,Object> getAllSchedulesOrderByStart(){
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap();

        hashMap.put("start9", slotRepository.findAllByStartTime(09.00));
        hashMap.put("start10", slotRepository.findAllByStartTime(10.00));
        hashMap.put("start11", slotRepository.findAllByStartTime(11.00));
        hashMap.put("start12", slotRepository.findAllByStartTime(12.00));
        hashMap.put("start13", slotRepository.findAllByStartTime(13.00));
        hashMap.put("start14", slotRepository.findAllByStartTime(14.00));
        hashMap.put("start15", slotRepository.findAllByStartTime(15.00));
        hashMap.put("start16", slotRepository.findAllByStartTime(16.00));
        hashMap.put("start17", slotRepository.findAllByStartTime(17.00));
        hashMap.put("start18", slotRepository.findAllByStartTime(18.00));
        hashMap.put("start19", slotRepository.findAllByStartTime(19.00));
        hashMap.put("start20", slotRepository.findAllByStartTime(20.00));
        hashMap.put("start21", slotRepository.findAllByStartTime(21.00));
        hashMap.put("start22", slotRepository.findAllByStartTime(22.00));
        hashMap.put("start23", slotRepository.findAllByStartTime(23.00));

        return hashMap;
    }

    public void deleteAll(){
        slotRepository.deleteAll();
    }
}
