package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Area;
import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.JobPost;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobPostService {

    @Autowired
    JobPostRepository jobPostRepository;

    public JobPost save(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public List<JobPost> findAll() {
        return jobPostRepository.findAll();
    }

    public JobPost get(Long id) {
        return jobPostRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        jobPostRepository.delete(jobPostRepository.getById(id));
    }

 /*
    public Page<JobPost> searchAllByTypeAndNotExpired(String type, LocalDate  currentDate, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndNotExpired(type, currentDate, pageable);
    }
    public Page<JobPost> searchAllByTypeAndNotExpiredAndCity(String type, LocalDate currentDate, String city, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndNotExpiredAndCity(type, currentDate, city, pageable);
    }


    public Page<JobPost> searchAllByTypeAndNotExpiredAndCategory(String type, LocalDate currentDate, Area category, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndNotExpiredAndCategory(type, currentDate, category, pageable);

    }

    public Page<JobPost> searchAllByTypeAndNotExpiredAndCategoryAndCity(String type, LocalDate currentDate, Area category, String city, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndNotExpiredAndCategoryAndCity(type, currentDate, category, city, pageable);

    }

    public  Page<JobPost> searchAllByTypeAndEmployer(String type,Employer employer,  Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndEmployer(type,employer, pageable);
    }

    public  Page<JobPost> searchAllByTypeAndEmployerAndNotExpired(String type, Employer employer, LocalDate currentDate, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndEmployerAndNotExpired(type,employer,currentDate, pageable);
    }

    public  Page<JobPost> searchAllByTypeAndEmployerAndExpired(String type, Employer employer, LocalDate currentDate, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndEmployerAndExpired(type,employer,currentDate, pageable);
    }

    public Page<JobPost> searchAllByTypeAndEmployerAndCategoryAllState(String type, Employer employer, Area categoryInstance, Pageable pageable) {
        return jobPostRepository.searchAllByTypeAndEmployerAndCategoryAllState( type, employer,  categoryInstance, pageable);
    }
*/

    public Page<JobPost> searchBasicStudent(String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable){
        return jobPostRepository.searchBasicStudent(type, currentDate, minSalary, maxSalary, pageable);
    }

    public Page<JobPost> searchQueryBasicStudent(String query, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable) {
        return jobPostRepository.searchQueryBasicStudent(query, type, currentDate, minSalary, maxSalary, pageable);
    }


    public Page<JobPost> searchCategoryBasicStudent(Area c, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable) {
        return jobPostRepository.searchCategoryBasicStudent(c, type, currentDate, minSalary, maxSalary, pageable);
    }

    public Page<JobPost> searchCategoryQueryBasicStudent(Area c, String query, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable) {
        return jobPostRepository.searchCategoryQueryBasicStudent(c, query, type, currentDate, minSalary, maxSalary, pageable);

    }

    public Page<JobPost> searchBasicEmployer(Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchBasicEmployer(employer, type, pageable);

    }

    public Page<JobPost> searchCategoryBasicEmployer(Area c, Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchCategoryBasicEmployer(c, employer, type, pageable);
    }

    public Page<JobPost> searchActiveBasicEmployer(LocalDate now, Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchActiveBasicEmployer(now, employer, type, pageable);
    }

    public Page<JobPost> searchNotActiveBasicEmployer(LocalDate now, Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchNotActiveBasicEmployer(now, employer, type, pageable);

    }

    public Page<JobPost> searchCategoryActiveBasicEmployer(Area c, LocalDate now, Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchCategoryActiveBasicEmployer(c, now, employer, type, pageable);

    }

    public Page<JobPost> searchCategoryNotActiveBasicEmployer(Area c, LocalDate now, Employer employer, String type, Pageable pageable) {
        return jobPostRepository.searchCategoryNotActiveBasicEmployer(c, now, employer, type, pageable);
    }

    public void deleteAll(){
        jobPostRepository.deleteAll();
    }


}
