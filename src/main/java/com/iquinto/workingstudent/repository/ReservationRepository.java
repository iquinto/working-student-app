package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);
    Reservation findByEmployerAndStudent(Employer employer, Student student);

    Page<Reservation> findAllByEmployer(Employer employer, Pageable pageable);

    Page<Reservation> findAllByStudent(Student student, Pageable pageable);

    @Query("select distinct r.student from Reservation r  where r.employer = :employer")
    Page<Student> findAllStudentsInReservationByEmployer(Employer employer, Pageable pageable);

}
