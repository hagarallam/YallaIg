package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.constants.VariableConstants;
import com.yallaIg.yallaIg_backend.validator.phone.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRegistrationRequestDto {

    @NotNull
    private Integer productId;

    @Min(value = 1,message = ErrorConstants.ERROR_INVALID_GRADE)
    @Max(value = 13,message = ErrorConstants.ERROR_INVALID_GRADE)
    private Integer studentGrade ;

    @NotEmpty
    @NotNull
    private String school ;

    @Email(regexp = VariableConstants.EMAIL_REGEX, message = ErrorConstants.ERROR_INVALID_EMAIL)
    private String parentEmail;

    @NotNull
    @NotEmpty
    @PhoneNumber
    private String parentNumber;

}
