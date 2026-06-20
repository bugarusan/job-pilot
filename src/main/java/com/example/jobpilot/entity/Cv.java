package com.example.jobpilot.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cv")
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    @Column(length =2000 )
    private String summary;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Skill> skills;
}
