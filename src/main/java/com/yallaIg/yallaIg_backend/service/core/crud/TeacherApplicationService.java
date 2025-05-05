package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.TeacherApplicationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.TeacherApplicationResponseDto;
import org.springframework.data.domain.Page;

public interface TeacherApplicationService {
    void submitApplication(TeacherApplicationRequestDto applicationRequestDto);

    Page<TeacherApplicationResponseDto> getAll(int page, int size);
}
