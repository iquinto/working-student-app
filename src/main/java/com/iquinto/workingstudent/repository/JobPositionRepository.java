package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findAll();

    JobPosition findByName(String name);


    @Query("select c from JobPosition c  where lower(c.name) like %:name%")
    List<JobPosition> findAllByNameLike(String name);
}
