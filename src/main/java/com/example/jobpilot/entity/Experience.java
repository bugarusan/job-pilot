package com.example.jobpilot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
