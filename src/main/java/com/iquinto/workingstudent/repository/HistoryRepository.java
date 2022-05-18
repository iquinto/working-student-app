package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.History;
import com.iquinto.workingstudent.model.Rating;
import com.iquinto.workingstudent.model.Student;
import com.iquinto.workingstudent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("select  n.month, count(n.month) from History n  where n.username = :username and n.year = :year group by n.month order by n.month asc")
    List<Object[]> findAllTotalsByYear(String username, int year);

    @Query("select  count(n.month) from History n  where n.username = :username and n.year = :year and n.month = :month group by n.month order by n.month asc")
    Long findAllTotalsByUsernameAndYearAndMonths(String username, int year, int month);
}
