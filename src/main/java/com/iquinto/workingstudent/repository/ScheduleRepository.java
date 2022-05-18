package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.Schedule;
import com.iquinto.workingstudent.model.Slot;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findById(Long id);
    List<Schedule> findAll();
    List<Schedule> findAllByStudent(Student student);
    void delete(Schedule schedule);

    @Transactional
    @Modifying
    @Query("delete from Schedule where reserve = :reserve and student = :student")
    void deleteByReserveAndStudent(boolean reserve,  Student student);

    //Schedule findByStudentAndSlot(Student student, Slot slot);
    Optional<Schedule> findScheduleByStudentAndSlot(Student student, Slot slot);

    Boolean existsBySlotAndReserveAndStudent(Slot slot, boolean reserve, Student student);


}
