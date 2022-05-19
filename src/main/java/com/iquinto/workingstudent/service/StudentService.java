package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.JobPosition;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.enums.DaysOfTheWeek;
import com.iquinto.workingstudent.model.enums.Province;
import com.iquinto.workingstudent.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {

 @Autowired
 StudentRepository studentRepository;

 @Autowired
 JobPositionService jobPositionService;

 public Student save(Student user) {
  Student userPersisted = studentRepository.save(user);
  return userPersisted;
 }

 public Student get(Long id) {
  return studentRepository.findById(id).orElse(null);
 }

 public Student findByUsername(String username) {
 return studentRepository.findByUsername(username);
 }
/*
 public Page<Student> searchAllByCategoryAndProvince(JobPosition jobPosition, Province province, Pageable pageable){
  return studentRepository.searchAllByCategoryAndProvince(jobPosition,province,pageable);
 };
*/
 public Page<Student> searchAllByCategoryAndProvinceAndCity(JobPosition jobPosition, Province province, String city, Pageable pageable){
  return studentRepository.searchAllByCategoryAndProvinceAndCity(jobPosition,province, city, pageable);
 };

 public void delete(Student student){
  studentRepository.delete(student);
 }

 public List<Student>  findAll() {
  return studentRepository.findAll();
 }

 /*** SEARCH BLOCK ***/

 public Page<Student> searchStartEnd( Double startTime, Double endTime, Pageable pageable){
  return studentRepository.searchStartEnd(startTime, endTime,  pageable);
 };

 public Page<Student> searchCategoryStartEnd(JobPosition jobPosition,  Double startTime, Double endTime, Pageable pageable){
  return studentRepository.searchCategoryStartEnd(jobPosition, startTime, endTime,  pageable);
 };

 public Page<Student> searchCityStartEnd(String city,  Double startTime, Double endTime, Pageable pageable){
  return studentRepository.searchCityStartEnd(city, startTime, endTime,  pageable);
 };
 public Page<Student> searchDayStartEnd(DaysOfTheWeek day,  Double startTime, Double endTime, Pageable pageable){
  return studentRepository.searchDayStartEnd(day, startTime, endTime,  pageable);
 };

 public Page<Student> searchDayCategoryStartEnd(DaysOfTheWeek daysOfTheWeek, JobPosition jobPosition,  Double startTime, Double endTime, Pageable pageable) {

  return studentRepository.searchDayCategoryStartEnd(daysOfTheWeek, jobPosition, startTime, endTime,  pageable);

 }

 public Page<Student> searchDayCityStartEnd(DaysOfTheWeek daysOfTheWeek, String city,  Double startTime, Double endTime, Pageable pageable) {
  return studentRepository.searchDayCityStartEnd(daysOfTheWeek, city, startTime, endTime,  pageable);

 }

 public Page<Student> searchCategoryCityStartEnd(JobPosition jobPosition, String city,  Double startTime, Double endTime, Pageable pageable) {
  return studentRepository.searchCategoryCityStartEnd(jobPosition, city, startTime, endTime,  pageable);

 }

 public Page<Student> searchDayCategoryCityStartEnd(DaysOfTheWeek daysOfTheWeek, JobPosition jobPosition, String city,  Double startTime, Double endTime, Pageable pageable) {
  return studentRepository.searchDayCategoryCityStartEnd(daysOfTheWeek, jobPosition, city, startTime, endTime,  pageable);
 }

 public void deleteAll(){
  studentRepository.deleteAll();
 }

}
