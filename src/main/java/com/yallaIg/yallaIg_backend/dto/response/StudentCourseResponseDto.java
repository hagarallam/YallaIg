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
public class StudentCourseResponseDto extends GeneralCourseResponseDto {

    private String link ;
}
