package com.examly.springapp.repository;

import com.examly.springapp.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.stereotype.Repository;


public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    @Query("SELECT a FROM Alumni a WHERE " +
            "(:query IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(a.currentCompany) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(a.jobTitle) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(a.skills) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:startYear IS NULL OR a.graduationYear >= :startYear) AND " +
            "(:endYear IS NULL OR a.graduationYear <= :endYear) AND " +
            "(:mentorship IS NULL OR a.availableForMentorship = :mentorship)")
    List<Alumni> searchAlumni(String query, Integer startYear, Integer endYear, Boolean mentorship);
}