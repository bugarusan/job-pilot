package com.example.jobpilot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resumes")
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    @Column(length = 2000)
    private String summary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL)
    private List<Skill> skills;
}
