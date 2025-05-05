package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.TeacherApplicationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.TeacherApplicationResponseDto;
import com.yallaIg.yallaIg_backend.model.TeacherApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {FileMapper.class})
public interface TeacherApplicationMapper {

    TeacherApplication requestDtoToTeacherApplication(TeacherApplicationRequestDto applicationRequestDto);

    TeacherApplicationResponseDto teacherApplicationToResponseDto(TeacherApplication teacherApplication);
}
