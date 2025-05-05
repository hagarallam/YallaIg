package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCodeResponseDto {

    private Integer phoneCodeId;
    private String phoneCode;
    private Integer countryId;
}
