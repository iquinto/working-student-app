package com.iquinto.workingstudent.service;
import com.iquinto.workingstudent.model.Resume;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class ResumeStorageService {
    @Autowired
    private ResumeRepository resumeRepository;

    public Resume save(Resume resume) throws IOException {
        return resumeRepository.save(resume);
    }

    public Resume getFile(String id) {
        return resumeRepository.findById(id);
    }

    /*
    public Resume findByStudent(Student student) {
        return resumeRepository.findByStudent(student);
    }
    */

    public void delete(Resume resume) {
        resumeRepository.delete(resume);
    }

    public Stream<Resume> findAll() {
        return resumeRepository.findAll().stream();
    }

    public void deleteAll(){
        resumeRepository.deleteAll();
    }

    public Stream<Resume> findAllByStudent(Student student) {
        return resumeRepository.findAllByStudent(student).stream();
    }
}
