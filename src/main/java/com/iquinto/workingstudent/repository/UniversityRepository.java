package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAll();

    University findByName(String name);

    @Query("select u from University u where lower(u.name) like %:name%")
    List<University> findAllByNameLike(String name);

}
