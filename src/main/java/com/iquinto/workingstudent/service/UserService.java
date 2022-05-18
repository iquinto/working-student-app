package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        Iterable<User> iterator = userRepository.findAll();
        List<User> userList = new ArrayList<User>();
        iterator.forEach(userList::add);
        return userList;
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        User user =  userRepository.findByUsername(username);
        return user;
    }

    public User save(User user) {
        User userPersisted = userRepository.save(user);
        return userPersisted;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

}

