package com.iquinto.workingstudent.repository;

import com.iquinto.workingstudent.model.Area;
import com.iquinto.workingstudent.model.Employer;
import com.iquinto.workingstudent.model.JobPost;
import com.iquinto.workingstudent.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type and (jp.expiration >= :currentDate or jp.expiration is null)")
    Page<JobPost> searchAllByTypeAndNotExpired(String type, LocalDate currentDate, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type and (jp.expiration >= :currentDate or jp.expiration is null) " +
            "and lower(jp.employer.address.city) like %:city%")
    Page<JobPost> searchAllByTypeAndNotExpiredAndCity(String type, LocalDate currentDate, String city, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type and (jp.expiration >= :currentDate or jp.expiration is null) " +
            "and jp.category = :category")
    Page<JobPost> searchAllByTypeAndNotExpiredAndCategory(String type, LocalDate currentDate, Area category, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type and (jp.expiration >= :currentDate or jp.expiration is null) " +
            "and jp.category = :category " +
            "and lower(jp.employer.address.city) like %:city%")
    Page<JobPost> searchAllByTypeAndNotExpiredAndCategoryAndCity(String type, LocalDate currentDate, Area category, String city, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where  jp.type = :type and jp.employer = :employer")
    Page<JobPost> searchAllByTypeAndEmployer(String type, Employer employer, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where  jp.type = :type and jp.employer = :employer and (jp.expiration >= :currentDate or jp.expiration is null)")
    Page<JobPost> searchAllByTypeAndEmployerAndNotExpired(String type, Employer employer, LocalDate currentDate, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type and jp.employer = :employer and jp.expiration < :currentDate")
    Page<JobPost> searchAllByTypeAndEmployerAndExpired(String type, Employer employer, LocalDate currentDate, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where   jp.type = :type and jp.employer = :employer and jp.category = :category")
    Page<JobPost> searchAllByTypeAndEmployerAndCategoryAllState(String type, Employer employer, Area category,  Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "left join  jp.employer employer " +
            "left  join  employer.address " +
            "where jp.type = :type and " +
            "jp.employer = :employer and " +
            "jp.category = :category")
    Page<JobPost> search(String type, Employer employer,Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.expiration > :currentDate " +
            "and (jp.yearSalary between :minSalary and :maxSalary) ")
    Page<JobPost> searchBasicStudent(String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.expiration > :currentDate " +
            "and (jp.yearSalary between :minSalary and :maxSalary) " +
            "and (lower(jp.employer.address.city) like %:query% " +
            "or lower(jp.title) like %:query%  or lower(jp.description) like %:query% )" )
    Page<JobPost> searchQueryBasicStudent(String query, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.expiration > :currentDate " +
            "and (jp.yearSalary between :minSalary and :maxSalary) " +
            "and jp.category = :c")
    Page<JobPost> searchCategoryBasicStudent(Area c, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.expiration > :currentDate " +
            "and (jp.yearSalary between :minSalary and :maxSalary) " +
            "and jp.category = :c " +
            "and (lower(jp.employer.address.city) like %:query% " +
            "or lower(jp.title) like %:query%  " +
            "or lower(jp.description) like %:query% )")
    Page<JobPost> searchCategoryQueryBasicStudent(Area c, String query, String type, LocalDate currentDate, Double minSalary, Double maxSalary, Pageable pageable);


    /**EMPLOYER BLOCK***/
    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer ")
    Page<JobPost> searchBasicEmployer(Employer employer, String type,  Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer " +
            "and jp.category = :c ")
    Page<JobPost> searchCategoryBasicEmployer(Area c, Employer employer, String type, Pageable pageable);


    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer " +
            "and jp.expiration > :now  ")
    Page<JobPost> searchActiveBasicEmployer(LocalDate now, Employer employer, String type, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer " +
            "and jp.expiration <= :now  ")
    Page<JobPost> searchNotActiveBasicEmployer(LocalDate now, Employer employer, String type, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer " +
            "and jp.expiration > :now  " +
            "and jp.category =:c")
    Page<JobPost> searchCategoryActiveBasicEmployer(Area c, LocalDate now, Employer employer, String type, Pageable pageable);

    @Query("select distinct jp from JobPost jp " +
            "where jp.type = :type " +
            "and jp.employer = :employer " +
            "and jp.expiration <= :now " +
            "and jp.category =:c")
    Page<JobPost> searchCategoryNotActiveBasicEmployer(Area c, LocalDate now, Employer employer, String type, Pageable pageable);


    List<JobPost> findAllByCandidates(Student student);
}
