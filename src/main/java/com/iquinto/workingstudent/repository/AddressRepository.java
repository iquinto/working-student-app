package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Address;
import com.iquinto.workingstudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressByUser(User user);
}
