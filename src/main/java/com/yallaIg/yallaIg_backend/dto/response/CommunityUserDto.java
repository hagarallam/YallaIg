package com.yallaIg.yallaIg_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityUserDto {
    private String fullName;
    private FileResponseDto userImage;
}
