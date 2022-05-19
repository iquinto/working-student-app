package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Notification;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.model.enums.Role;
import com.iquinto.workingstudent.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAllByDestinationAndRead(User destination, boolean read) {
        return  notificationRepository.findAllByDestinationAndRead(destination, read);
    }

    public List<Notification> findAllByDestination(User destination) {
        return  notificationRepository.findAllByDestination(destination);
    }

    public List<Notification> findAllByDestinationAndReadAndInSubjects(User destination, boolean read, List<String> subjects) {
        return  notificationRepository.findAllByDestinationAndReadAndInSubjects(destination, read, subjects);
    }

    public Notification findById(Long id) {
        return  notificationRepository.findById(id).orElse(null);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void delete(Notification notification) {
         notificationRepository.delete(notification);
    }

    public void deleteAll(){
        notificationRepository.deleteAll();
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
}
