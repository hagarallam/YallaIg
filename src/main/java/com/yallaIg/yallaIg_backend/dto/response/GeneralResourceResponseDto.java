package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralResourceResponseDto {

    private Integer resourceId;
    private String title;
    private String description ;
    private Double price;
    private FileResponseDto resourceImage;
    private ResourcesCategoryEnum resourcesCategoryEnum;
}
