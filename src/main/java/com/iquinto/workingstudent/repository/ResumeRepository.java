package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Resume;
import com.iquinto.workingstudent.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Resume findById(String id);
    Resume findByStudent(Student student);
    List<Resume> findAll();
    List<Resume> findAllByStudent(Student student);
}
