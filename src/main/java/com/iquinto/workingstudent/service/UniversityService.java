package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.University;
import com.iquinto.workingstudent.repository.UniversityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UniversityService {

    @Autowired
    UniversityRepository universityRepository;

    public List<University> findAll() {
        return universityRepository.findAll();
    }

    public Set<University> findAllAsSet() {
        return (Set<University>) universityRepository.findAll();
    }

    public University save(University university){
        return universityRepository.save(university);
    }

    public void delete(Long id){
        universityRepository.delete(universityRepository.getById(id));
    }

    public University findById(Long id) {
        return universityRepository.findById(id).orElse(null);
    }

    public University findByName(String name) {
        return universityRepository.findByName(name);
    }

    public List<University>  findAllByNameLike(String name) {
        return universityRepository.findAllByNameLike(name);
    }

    public void deleteAll(){
        universityRepository.deleteAll();
    }

}
