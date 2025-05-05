package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.constants.VariableConstants;
import com.yallaIg.yallaIg_backend.validator.password.MatchPassword;
import com.yallaIg.yallaIg_backend.validator.phone.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MatchPassword
public class UserChangePasswordRequestDto {

    @NotNull
    @NotEmpty
    private String currentPassword ;

    @NotNull
    @NotEmpty
    @Pattern(regexp = VariableConstants.PASSWORD_REGEX,message = ErrorConstants.ERROR_INVALID_PASSWORD)
    private String newPassword ;

    @NotNull
    @NotEmpty
    private String matchingPassword;

}
