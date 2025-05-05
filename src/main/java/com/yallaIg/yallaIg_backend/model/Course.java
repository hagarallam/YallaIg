package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "name")
    private String name ;

    @Column(name = "price")
    private Double price;

    @Column(name = "link")
    private String link ;

    @Column(name = "start_date")
    private Date startDate ;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "exam_session_id",referencedColumnName = "exam_session_id")
    private ExamSession examSession;

    @ManyToOne
    @JoinColumn(name = "subject_id",referencedColumnName = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "level_id",referencedColumnName = "level_id")
    private Level level;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File courseFile;

    @ManyToMany(mappedBy = "courses")
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Lesson> lessons;


}
