package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double salary;

    private String requiredSkills;
    private LocalDate expiryDate;
    @Column(name = "is_active")
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User user;
}