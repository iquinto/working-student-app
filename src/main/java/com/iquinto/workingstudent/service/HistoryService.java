package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.History;
import com.iquinto.workingstudent.model.Rating;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.repository.HistoryRepository;
import com.iquinto.workingstudent.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public History save(History history) {
        return  historyRepository.save(history);
    }

    public  List<Long> listTotalsByYearAndMonths(String username, int year) {
        List<Long> totals = new ArrayList<Long>();
        for (int month : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)){
            Long val = historyRepository.findAllTotalsByUsernameAndYearAndMonths(username, year, month) ;
            if(val == null)
                totals.add(0L);
            else
                totals.add(val);
        }

        return totals;
    }

        /*
    public LinkedHashMap<String,Object> listTotalsByYear(String username, int year) {
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap();
        List<Integer> month = new ArrayList<>();
        List<Long> value = new ArrayList<>();

        for (Object[] ob : historyRepository.findAllTotalsByYear(username, year)){
            month.add((Integer) ob[0]);
            value.add((Long) ob[1]);
        }

        hashMap.put("month", month);
        hashMap.put("value", value);



        return hashMap;
    }
    */

    public void deleteAll(){
        historyRepository.deleteAll();
    }

    public List<History> findAll() {
        return historyRepository.findAll();
    }
}
