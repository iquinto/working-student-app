package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.JobPosition;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
import com.iquinto.workingstudent.model.enums.Province;
import com.iquinto.workingstudent.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public interface StudentRepository  extends JpaRepository<Student, Long> {

    Student findByName(Role name);

    Student findByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<Student> findAllByJobPositionsContaining(JobPosition jobPosition);

    List<Student> findAllByJobPositionsContaining(JobPosition jobPosition, Pageable pageable);

    @Query("select distinct st from Student st " +
            "left join st.jobPositions ca " +
            "left join st.address ad " +
            "where ca =:jobPosition " +
            "and ad.province = :province")
    Page<Student> searchAllByCategoryAndProvince(JobPosition jobPosition, Province province, Pageable pageable);

    @Query("select distinct st from Student st " +
            "left join st.jobPositions ca " +
            "left join st.address ad " +
            "where ca =:jobPosition " +
            "and ad.province = :province " +
            "and lower(ad.city) like %:city%")
    Page<Student> searchAllByCategoryAndProvinceAndCity(JobPosition jobPosition, Province province, String city, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day is not null " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchStartEnd( Double startTime, Double endTime, Pageable pageable);


    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where jobPosition = :jobPosition " +
            "and slot.day is not null " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchCategoryStartEnd(JobPosition jobPosition,  Double startTime, Double endTime, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where lower(address.city) like %:city%  " +
            "and slot.day is not null " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchCityStartEnd(String city,  Double startTime, Double endTime, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day = :daysOfTheWeek " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchDayStartEnd(DaysOfTheWeek daysOfTheWeek, Double startTime, Double endTime, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day = :daysOfTheWeek " +
            "and jobPosition = :jobPosition " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchDayCategoryStartEnd(DaysOfTheWeek daysOfTheWeek, JobPosition jobPosition,  Double startTime, Double endTime, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day = :daysOfTheWeek " +
            "and lower(address.city) like %:city% " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime order by student.id asc")
    Page<Student> searchDayCityStartEnd(DaysOfTheWeek daysOfTheWeek, String city,  Double startTime, Double endTime, Pageable pageable);

    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day is not null " +
            "and lower(address.city) like %:city% " +
            "and jobPosition = :jobPosition " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime  order by student.id asc")
    Page<Student> searchCategoryCityStartEnd(JobPosition jobPosition, String city,  Double startTime, Double endTime, Pageable pageable);


    @Query("select distinct student from Schedule schedule " +
            "left join schedule.slot slot " +
            "left join schedule.student student " +
            "left join student.jobPositions jobPosition  " +
            "left join student.address address " +
            "where slot.day = :daysOfTheWeek " +
            "and lower(address.city) like %:city% " +
            "and jobPosition = :jobPosition " +
            "and slot.startTime >= :startTime " +
            "and slot.endTime <= :endTime  order by student.id asc")
    Page<Student> searchDayCategoryCityStartEnd(DaysOfTheWeek daysOfTheWeek, JobPosition jobPosition, String city,  Double startTime, Double endTime, Pageable pageable);

}
