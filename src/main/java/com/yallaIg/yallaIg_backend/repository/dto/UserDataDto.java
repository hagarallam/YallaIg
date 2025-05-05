package com.yallaIg.yallaIg_backend.repository.dto;

import com.yallaIg.yallaIg_backend.model.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDataDto {
    private String fullName ;
    private String email;
    private String phone;
    private int countryId;
    private int phoneCodeId;
    private double userBalance ;
    private File userImage;

    public UserDataDto(String fullName, String email, String phone, int countryId,
                       int phoneCodeId, double userBalance, File userImage) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.countryId = countryId;
        this.phoneCodeId = phoneCodeId;
        this.userBalance = userBalance;
        this.userImage = userImage;
    }
}
