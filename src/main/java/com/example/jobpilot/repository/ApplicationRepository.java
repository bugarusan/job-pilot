package com.example.jobpilot.repository;

import com.example.jobpilot.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
