package com.example.jobpilot.repository;

import com.example.jobpilot.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByCompanyContainingIgnoreCase(String company);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findByDescriptionContainingIgnoreCase(String description);

    @Query("""
            SELECT DISTINCT j
            FROM Job j
            JOIN j.requiredSkills s
            WHERE LOWER(s) LIKE LOWER(CONCAT('%', :skill, '%'))
            """)
    List<Job> findBySkill(@Param("skill") String skill);

    boolean existsByUrl(String url);
}