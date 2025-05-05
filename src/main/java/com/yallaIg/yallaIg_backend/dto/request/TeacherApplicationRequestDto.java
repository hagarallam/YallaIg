package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.constants.VariableConstants;
import com.yallaIg.yallaIg_backend.model.File;
import com.yallaIg.yallaIg_backend.validator.file.ValidFile;
import com.yallaIg.yallaIg_backend.validator.phone.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherApplicationRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 3 , message = ErrorConstants.ERROR_INVALID_NAME)
    private String name;

    @NotNull
    @NotEmpty
    @Email(regexp = VariableConstants.EMAIL_REGEX, message = ErrorConstants.ERROR_INVALID_EMAIL)
    private String email;

    @NotNull
    @NotEmpty
    @PhoneNumber
    private String phone;

    @NotNull
    @NotEmpty
    private String specialization;

    @ValidFile(
            allowedTypes = {"application/pdf"},
            maxSize = 5 * 1024 * 1024,
            notEmpty = true
    )
    private MultipartFile cvFile;
}
