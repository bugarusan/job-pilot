package com.example.jobpilot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String level;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
