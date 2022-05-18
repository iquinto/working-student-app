package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByStudent(Student student);

    @Query("select  AVG(r.rate) from Rating r  where r.student = :student")
    Double getAverage(Student student);

}
