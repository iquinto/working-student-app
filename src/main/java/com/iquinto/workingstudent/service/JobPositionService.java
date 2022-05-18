package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.JobPosition;
import com.iquinto.workingstudent.repository.JobPositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class JobPositionService {

    @Autowired
    JobPositionRepository jobPositionRepository;

    public List<JobPosition> findAll() {
        return jobPositionRepository.findAll();
    }

    public Set<JobPosition> findAllAsSet() {
        return (Set<JobPosition>) jobPositionRepository.findAll();
    }

    public JobPosition save(JobPosition jobPosition){
        return jobPositionRepository.save(jobPosition);
    }

    public JobPosition findById(Long id) {
        return jobPositionRepository.getById(id);
    }

    public JobPosition findByName(String name) {
        return jobPositionRepository.findByName(name);
    }

    public List<JobPosition>  findAllByNameLike(String name) {
        return jobPositionRepository.findAllByNameLike(name);
    }

    public void delete(Long id){
        jobPositionRepository.delete(jobPositionRepository.getById(id));
    }

    public void deleteAll(){
        jobPositionRepository.deleteAll();
    }

}
