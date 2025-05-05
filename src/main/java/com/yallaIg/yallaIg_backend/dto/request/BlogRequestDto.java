package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.validator.file.ValidFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogRequestDto {

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotNull
    @NotEmpty
    private String content;

    @ValidFile(
            allowedTypes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            maxSize = 5 * 1024 * 1024,
            notEmpty = false
    )
    private MultipartFile blogFile;


    private SubjectEnum blogCategory;
}
