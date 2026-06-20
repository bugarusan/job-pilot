package com.example.jobpilot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "educations")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String school;

    private String degree;

    private String field;

    private LocalDate startDate;

    private  LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
