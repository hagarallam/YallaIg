package com.yallaIg.yallaIg_backend.dto.request;
import com.yallaIg.yallaIg_backend.validator.file.ValidFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class InstructorRequestDto {

    @NotEmpty
    @NotNull
    private String firstName ;

    @NotEmpty
    @NotNull
    private String lastName ;

    @NotEmpty
    @NotNull
    private String bio;

    @ValidFile(
            allowedTypes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            maxSize = 5 * 1024 * 1024,
            notEmpty = false
    )
    private MultipartFile instructorImage;
}
