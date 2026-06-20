package com.example.jobpilot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "experiences")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
