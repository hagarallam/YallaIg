package com.yallaIg.yallaIg_backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstructorResponseDto {

    private Integer instructorId;
    private String firstName ;
    private String lastName;
    private String bio;
    private FileResponseDto instructorImage;

}
