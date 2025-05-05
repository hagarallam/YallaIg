package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthenticationResponseDto {

    private String token ;
    private String refreshToken;
    private RoleEnum roleEnum;
    private Long accessTokenExpirationDate;
    private Long refreshTokenExpirationDate;
}
