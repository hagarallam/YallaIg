package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogResponseDto {

    private Integer blogId;
    private String title;
    private String content ;
    private FileResponseDto blogFile;
    private SubjectEnum blogCategory;

}
