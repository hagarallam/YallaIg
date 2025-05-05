package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentCourseResponseDto;
import com.yallaIg.yallaIg_backend.model.StudentCourse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentCourseService {
    List<StudentCourseResponseDto> getAllCoursesByUser(int page , int size);

    List<StudentCourse> getByOrderId(Integer orderId);

    List<StudentCourse> getTodayEnrollment();

    boolean checkIfRegisterdCourse(Integer courseId);

    StudentCourseResponseDto getCourseByIdAndUser(Integer courseId);
}
