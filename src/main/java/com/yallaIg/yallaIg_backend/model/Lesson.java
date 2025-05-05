package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lessons")
public class Lesson extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(name = "name")
    private String name ;

    @Column(name = "price")
    private Double price;

    @Column(name = "link")
    private String link ;

    @Column(name = "description")
    private String description;

    @Column(name = "lesson_date")
    private Date lessonDate ;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;



}
