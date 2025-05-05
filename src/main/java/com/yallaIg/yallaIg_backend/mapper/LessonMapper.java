package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.response.LessonResponseDto;
import com.yallaIg.yallaIg_backend.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {InstructorMapper.class, CourseMapper.class})
public interface LessonMapper {

    LessonResponseDto lessonToLessonResponseDto(Lesson lesson);
}
