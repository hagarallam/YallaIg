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

@Entity
@Table(name = "students_courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId studentCourseId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId(value = "studentId")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @MapsId(value = "courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "student_grade")
    private Integer studentGrade ;

    @Column(name = "school")
    private String school ;

    @Column(name = "parent_email")
    private String parentEmail;

    @Column(name = "parent_number")
    private String parentNumber;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date",updatable = false)
    private Date creationDate;


}
