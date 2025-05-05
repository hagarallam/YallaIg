package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.model.Course;
import com.yallaIg.yallaIg_backend.model.Instructor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonRequestDto {

    private Integer lessonId;
    private Integer instructorId;
    private Integer courseId;
    private String name ;
    private Double price;
    private String link ;
    private String description;
    private Date lessonDate ;
}
