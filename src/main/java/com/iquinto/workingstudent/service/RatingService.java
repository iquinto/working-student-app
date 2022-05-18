package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Notification;
import com.iquinto.workingstudent.model.Rating;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.repository.NotificationRepository;
import com.iquinto.workingstudent.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> findAllByStudent(Student student) {
        return  ratingRepository.findAllByStudent(student);
    }

    public Rating save(Rating rating) {
        return  ratingRepository.save(rating);
    }

    public Double getAverage(Student student) {
        return  ratingRepository.getAverage(student);
    }

    public void deleteAll(){
        ratingRepository.deleteAll();
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
}
