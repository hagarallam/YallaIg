package com.yallaIg.yallaIg_backend.dto.request;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.validator.phone.PhoneNumber;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactUsRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 3 , message = ErrorConstants.ERROR_INVALID_NAME)
    private String name;

    @NotNull
    @NotEmpty
    @PhoneNumber
    private String phone;

    @NotNull
    @NotEmpty
    private String description ;

}
