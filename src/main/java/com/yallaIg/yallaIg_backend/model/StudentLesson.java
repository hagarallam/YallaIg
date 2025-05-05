package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students_lessons")
public class StudentLesson  {

    @EmbeddedId
    private StudentLessonId studentLessonId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId(value = "studentId")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @MapsId(value = "lessonId")
    private Lesson lesson;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

}
