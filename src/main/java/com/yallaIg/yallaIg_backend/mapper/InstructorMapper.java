package com.yallaIg.yallaIg_backend.mapper;
import com.yallaIg.yallaIg_backend.dto.request.InstructorRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.InstructorResponseDto;
import com.yallaIg.yallaIg_backend.model.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {LessonMapper.class, CourseMapper.class, FileMapper.class})
public interface InstructorMapper {


    @Mapping(target = "instructorId",ignore = true)
    @Mapping(target = "courses",ignore = true)
    @Mapping(target = "lessons",ignore = true)
    Instructor instructorRequestDtoToInstructor(InstructorRequestDto instructorRequestDto);

    InstructorResponseDto instructorToInstructorResponseDto(Instructor instructor);
}
