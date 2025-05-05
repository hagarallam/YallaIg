package com.yallaIg.yallaIg_backend.validator.password;

import com.yallaIg.yallaIg_backend.dto.request.RegisterRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ResetPasswordRequest;
import com.yallaIg.yallaIg_backend.dto.request.UserChangePasswordRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;

import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<MatchPassword, Object> {


    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String password = "", matchingPassword = "";
        if(object instanceof RegisterRequestDto registerRequestDto){
            password = registerRequestDto.getPassword();
            matchingPassword = registerRequestDto.getMatchingPassword();
        }else if(object instanceof UserChangePasswordRequestDto userChangePasswordRequestDto){
            password = userChangePasswordRequestDto.getNewPassword();
            matchingPassword = userChangePasswordRequestDto.getMatchingPassword();

        }
        else if(object instanceof ResetPasswordRequest request){
            password = request.getNewPassword();
            matchingPassword = request.getMatchingPassword();

        }
        return Objects.equals(password ,matchingPassword);
    }
}
