package com.yallaIg.yallaIg_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Integer userId;
    private String fullName ;
    private String email;
    private String phone;
    private int countryId;
    private int phoneCodeId;
    private double userBalance ;
    private FileResponseDto userImage;
    private List<Integer> likedPostIds ;
}
