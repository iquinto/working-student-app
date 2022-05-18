package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findAll();

    Area findByName(String name);

    @Query("select c from Area c  where lower(c.name) like %:name%")
    List<Area> findAllByNameLike(String name);
}
