package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.model.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherApplicationResponseDto {

    private Integer applicationId;

    private String name;

    private String email;

    private String phone;

    private String specialization;

    private File cvFile;
}
