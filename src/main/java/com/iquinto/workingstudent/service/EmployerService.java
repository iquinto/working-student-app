package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.repository.EmployerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployerService {

    @Autowired
    EmployerRepository employerRepository;

    public Employer save(Employer user) {
        Employer userPersisted = employerRepository.save(user);
        return userPersisted;
    }

    public Employer get(Long id) {
        Employer user = employerRepository.getById(id);
        return user;
    }

    public Employer findByUsername(String username) {
       return employerRepository.findByUsername(username);
    }

    public void delete(Employer employer){
        employerRepository.delete(employer);
    }

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public void deleteAll(){
        employerRepository.deleteAll();
    }
}
