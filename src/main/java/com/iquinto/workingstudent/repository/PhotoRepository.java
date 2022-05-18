package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Photo;
import com.iquinto.workingstudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findById(String id);

    Photo findByUser(User user);
}
