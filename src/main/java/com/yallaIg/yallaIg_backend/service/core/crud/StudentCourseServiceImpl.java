package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.response.CourseResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentCourseResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.CourseMapper;
import com.yallaIg.yallaIg_backend.model.Course;
import com.yallaIg.yallaIg_backend.model.StudentCourse;
import com.yallaIg.yallaIg_backend.repository.CourseRepository;
import com.yallaIg.yallaIg_backend.repository.StudentCourseRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    
    @Override
    public List<StudentCourseResponseDto> getAllCoursesByUser(int page, int size) {
        Integer userId = SecurityUtil.getCurrentUserId();
        List<Integer> ids = getCoursesIds(userId);
        List<Course> courses = courseRepository.findAllByCourseIdIn(ids);
        return courses.stream().map(courseMapper::courseToStudentCourseResponseDto).toList();
    }

    private List<Integer> getCoursesIds(Integer userId) {
        List<StudentCourse> registeredCourses = studentCourseRepository.findAllByStudentCourseIdStudentId(userId);
        return registeredCourses.stream().map(course -> course.getCourse().getCourseId()).toList();
    }

    @Override
    public StudentCourseResponseDto getCourseByIdAndUser(Integer courseId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        Integer id = getCourseId(userId,courseId);
        Optional<Course> course = courseRepository.findById(id);
        return course.map(courseMapper::courseToStudentCourseResponseDto).orElseThrow( () -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }


    private Integer getCourseId(Integer userId,Integer courseId) {
        Optional<StudentCourse> studentCourse = studentCourseRepository.findByStudentCourseIdStudentIdAndStudentCourseIdCourseId(userId,courseId);
        return studentCourse.map(studentCourse1 -> studentCourse1.getStudentCourseId().getCourseId()).orElseThrow( () -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public List<StudentCourse> getByOrderId(Integer orderId) {
        return studentCourseRepository.findAllByOrderOrderId(orderId);
    }



    @Override
    public List<StudentCourse> getTodayEnrollment() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return studentCourseRepository.findByCreationDateBetween(startOfDay,endOfDay);
    }

    @Override
    public boolean checkIfRegisterdCourse(Integer courseId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        Optional<StudentCourse> studentCourse = studentCourseRepository.findByStudentCourseIdStudentIdAndStudentCourseIdCourseId(userId,courseId);
        return studentCourse.isPresent();
    }
}
