package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Address;
import com.iquinto.workingstudent.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address save(Address address) {
        Address addressPersited = addressRepository.save(address);
        return addressPersited;
    }

    public Address update(Address address) {
        Address addressPersited = get(address.getId());
        addressPersited.setStreet(address.getStreet());
        addressPersited.setZipcode(address.getZipcode());
        addressPersited.setCity(address.getCity());
        addressPersited.setProvince(address.getProvince());
        addressPersited.setCountry(address.getCountry());

        return addressRepository.save(addressPersited);
    }

    public Address get(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void delete(Address address){
        addressRepository.delete(address);
    }

    public void deleteAll(){
        addressRepository.deleteAll();
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
