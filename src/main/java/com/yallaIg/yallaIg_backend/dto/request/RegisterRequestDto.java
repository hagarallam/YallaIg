package com.yallaIg.yallaIg_backend.dto.request;
import com.yallaIg.yallaIg_backend.constants.VariableConstants;
import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.validator.file.ValidFile;
import com.yallaIg.yallaIg_backend.validator.password.MatchPassword;
import com.yallaIg.yallaIg_backend.validator.phone.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MatchPassword
public class RegisterRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 5 , message = ErrorConstants.ERROR_INVALID_NAME)
    private String fullName;

    @NotNull
    @NotEmpty
    @Email(regexp = VariableConstants.EMAIL_REGEX, message = ErrorConstants.ERROR_INVALID_EMAIL)
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = VariableConstants.PASSWORD_REGEX,message = ErrorConstants.ERROR_INVALID_PASSWORD)
    private String password ;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @PhoneNumber
    private String phone;
    @NotNull
    private Integer phoneCodeId;
    @NotNull
    private Integer countryId;

    @ValidFile(
            allowedTypes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            maxSize = 5 * 1024 * 1024,
            notEmpty = false
    )
    private MultipartFile userImage;


}
