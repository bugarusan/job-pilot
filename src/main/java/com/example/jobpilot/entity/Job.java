package com.example.jobpilot.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String company;

    @Column(length = 2000)
    private String description;

    private String location;

    private String Salary;

    private String requiredSkills;

    private String url;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL)
    private List<Application> applications;
}
