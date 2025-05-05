package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.enums.LevelEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.model.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralCourseResponseDto {

    private Integer courseId;
    private String name ;
    private Double price;
    private Date startDate ;
    private Date endDate;
    private Date creationDate;
    private Date lastModificationDate;
    private String description;
    private ExamSessionEnum examSessionEnum;
    private SubjectEnum subjectEnum;
    private LevelEnum levelEnum;
    private File courseFile;
    private List<InstructorResponseDto> instructors;
}
