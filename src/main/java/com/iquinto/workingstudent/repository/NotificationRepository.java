package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Notification;
import com.iquinto.workingstudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findById(Long id);
    List<Notification> findAllByDestinationAndRead(User destination, boolean read);
    List<Notification> findAllByDestination(User destination);

    @Query("select n from Notification n  where n.destination = :destination and n.read = :read and n.subject in :subjects order by n.created desc")
    List<Notification> findAllByDestinationAndReadAndInSubjects(User destination, boolean read, List<String> subjects);



}
