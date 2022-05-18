package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Area;
import com.iquinto.workingstudent.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AreaService {

    @Autowired
    AreaRepository areaRepository;

    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    public Area save(Area area){
        return areaRepository.save(area);
    }

    public void delete(Long id){
        Area a = areaRepository.getById(id);
        areaRepository.delete(a);
    }

    public Area findById(Long id) {
        return areaRepository.findById(id).orElse(null);
    }

    public Area findByName(String name) {
        return areaRepository.findByName(name);
    }

    public List<Area> findAllByNameLike(String name) {
        return areaRepository.findAllByNameLike(name);
    }

    public void deleteAll(){
        areaRepository.deleteAll();
    }

}
