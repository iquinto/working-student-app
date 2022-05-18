package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Employer findByName(Role name);

    Employer findByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("select u.name from User u where u.id in (:pIdList)")
    List<String> findByIdList(@Param("pIdList") List<Long> idList);

}
