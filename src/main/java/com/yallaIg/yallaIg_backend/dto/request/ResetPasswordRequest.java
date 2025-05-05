package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.constants.VariableConstants;
import com.yallaIg.yallaIg_backend.validator.password.MatchPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MatchPassword
public class ResetPasswordRequest {

    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    @NotEmpty
    @Pattern(regexp = VariableConstants.PASSWORD_REGEX,message = ErrorConstants.ERROR_INVALID_PASSWORD)
    private String newPassword;

    @NotNull
    @NotEmpty
    private String matchingPassword;

}
