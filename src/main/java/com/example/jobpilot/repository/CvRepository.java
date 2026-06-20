package com.example.jobpilot.repository;

import com.example.jobpilot.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv,Long> {
}
