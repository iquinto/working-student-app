package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.Schedule;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.Reservation;
import com.iquinto.workingstudent.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SlotService slotService;

    @Autowired
    ScheduleService scheduleService;

    public Reservation findById(Long id){
       return reservationRepository.getById(id);
    }

    public Employer employer(Long id){
        return reservationRepository.getById(id).getEmployer();
    }

    public Map findByIdMap(Long id){
        Reservation r = reservationRepository.getById(id);
        Map reservation = new HashMap();
        ArrayList<Map> schedules = new ArrayList<>();
        for (Schedule s: r.getSchedules()) {
            Map val = new HashMap();
            val.put("slotId",s.getSlot().getId());
            val.put("employer",r.getEmployer().getUsername());
            val.put("reserve",s.isReserve());
            schedules.add(val);
        }

        reservation.put("schedules", schedules);
        reservation.put("startDate", r.getStartDate());
        reservation.put("numberOfWeeks", r.getNumberOfWeeks());
        reservation.put("schedules", schedules);
        reservation.put("id", r.getId());

        return reservation;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Page<Student> findAllStudentsInReservationByEmployer(Employer employer, Pageable pageable){
        return reservationRepository.findAllStudentsInReservationByEmployer(employer, pageable);
    }

    public Reservation save(Reservation s) {
        return reservationRepository.save(s);
    }

    public void delete(Reservation r) {
        reservationRepository.delete(r);
    }

    public  Page<Reservation> findAllByEmployer(Employer employer, Pageable pageable) {
        return reservationRepository.findAllByEmployer(employer, pageable);
    }

    public  Page<Reservation> findAllByStudent(Student student, Pageable pageable) {
        return reservationRepository.findAllByStudent(student, pageable);
    }

    public void deleteAll(){
        reservationRepository.deleteAll();
    }

}
