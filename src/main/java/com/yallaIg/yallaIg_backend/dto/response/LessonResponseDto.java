package com.yallaIg.yallaIg_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonResponseDto {
    private Integer lessonId;
    private String name ;
    private Double price;
    private String link ;
    private String description;
    private Date lessonDate ;
    private Date creationDate;
    private InstructorResponseDto instructor;
    private CourseResponseDto course;

}
