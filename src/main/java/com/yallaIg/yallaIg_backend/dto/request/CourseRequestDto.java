package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.enums.LevelEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.validator.file.ValidFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRequestDto {

    @NotNull
    @NotEmpty
    private String name ;

    @NotNull
    private Double price;

    @NotNull
    @NotEmpty
    private String link ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate ;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String description;

    @NotNull
    private ExamSessionEnum examSessionEnum;

    @NotNull
    private SubjectEnum subjectEnum;

    @NotNull
    private LevelEnum levelEnum;

    @ValidFile(
            allowedTypes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            maxSize = 5 * 1024 * 1024,
            notEmpty = false
    )
    private MultipartFile courseImage;

    @NotNull
    @NotEmpty
    private List<Integer> instructorIds;
}
