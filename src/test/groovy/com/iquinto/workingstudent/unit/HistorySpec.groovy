package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.History
import spock.lang.Specification

import java.time.LocalDate

class HistorySpec extends Specification {

    def "test create new history object"(){
        when:
        LocalDate date = LocalDate.now()
        History history = new History("test_student1", 1L, date.getMonthValue(), date.getYear(), date);

        then:
        history != null
    }

    def "test create new history object using setter"(){
        when:
        LocalDate date = LocalDate.now()
        History history = new History();
        history.setId(1)
        history.setUsername("test_student1")
        history.setReservationId(1L)
        history.setMonth(date.getMonthValue())
        history.setYear(date.getYear())
        history.setDateCreated(date)

        then:
        history != null
        history.id != null
        history.username != null
        history.reservationId != null
        history.month != null
        history.year  != null
        history.dateCreated != null
    }
}
