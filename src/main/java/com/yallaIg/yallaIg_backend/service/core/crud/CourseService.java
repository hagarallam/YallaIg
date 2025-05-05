package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.CourseRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {


    List<CourseResponseDto> getAllCourses();

    CourseResponseDto findById(Integer id);

    Integer createCourse(CourseRequestDto courseRequestDto);

    void deleteCourse(Integer id);

    void updateCourse(Integer id, CourseRequestDto courseRequestDto);
}
