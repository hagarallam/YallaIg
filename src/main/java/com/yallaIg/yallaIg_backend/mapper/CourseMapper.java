package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.CourseRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentCourseResponseDto;
import com.yallaIg.yallaIg_backend.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {InstructorMapper.class, FileMapper.class})
public interface CourseMapper {

    @Mapping(target = "examSessionEnum" ,source = "examSession.examSessionValue")
    @Mapping(target = "subjectEnum" , source = "subject.subjectEnum")
    @Mapping(target = "levelEnum", source = "level.levelEnum")
    CourseResponseDto courseToCourseResponseDto(Course course);


    @Mapping(target = "examSessionEnum" ,source = "examSession.examSessionValue")
    @Mapping(target = "subjectEnum" , source = "subject.subjectEnum")
    @Mapping(target = "levelEnum", source = "level.levelEnum")
    StudentCourseResponseDto courseToStudentCourseResponseDto(Course course);


    @Mapping(target = "courseId",ignore = true)
    @Mapping(target = "instructors" ,ignore = true)
    Course courserRequestDtoToCourse(CourseRequestDto courseRequestDto);


}
