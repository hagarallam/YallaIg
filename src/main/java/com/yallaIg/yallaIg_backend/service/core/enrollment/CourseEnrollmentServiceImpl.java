package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.model.*;
import com.yallaIg.yallaIg_backend.repository.CourseRepository;
import com.yallaIg.yallaIg_backend.repository.OrderRepository;
import com.yallaIg.yallaIg_backend.repository.StudentCourseRepository;
import com.yallaIg.yallaIg_backend.service.core.crud.UserService;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService{

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final StudentCourseRepository studentCourseRepository;
    private final OrderRepository orderRepository;

    @Override
    public EnrollmentResponse enroll(ProductRegistrationRequestDto productRegistrationRequestDto, int orderId) {

        // get course Object
        Optional<Course> course = courseRepository.findById(productRegistrationRequestDto.getProductId());

        if(!course.isPresent())
            return null;

        // get current User
        User student = userService.findByUserId(SecurityUtil.getCurrentUserId());

        // create and save StudentCourse Object
        StudentCourse studentCourse = createStudentCourse(student,course.get(),productRegistrationRequestDto,orderId);
        studentCourseRepository.save(studentCourse);
        return createEnrollmentResponse(course.get(),student.getUserId(),student.getFullName());
    }


    private StudentCourse createStudentCourse(User student, Course course,ProductRegistrationRequestDto productRegistrationRequestDto, int orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if(!order.isPresent())
            return null ;

        //create Composite key
        StudentCourseId id = createStudentCourseId(student.getUserId(), course.getCourseId());

        // create object
        StudentCourse studentCourse = createStudentCourseObject(student, course, productRegistrationRequestDto);
        studentCourse.setStudentCourseId(id);
        studentCourse.setOrder(order.get());
        return studentCourse;
    }
    private EnrollmentResponse createEnrollmentResponse(Course course,Integer userId,String fullName) {
        EnrollmentResponse enrollmentResponse = new EnrollmentResponse();
        enrollmentResponse.setUserFullName(fullName);
        enrollmentResponse.setUserId(userId);
        enrollmentResponse.setCourseName(course.getName());
        enrollmentResponse.setStartDate(course.getStartDate());
        enrollmentResponse.setEndDate(course.getEndDate());
        enrollmentResponse.setLink(course.getLink());
        return enrollmentResponse;
    }


    private static StudentCourse createStudentCourseObject(User student, Course course,ProductRegistrationRequestDto productRegistrationRequestDto) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setUser(student);
        studentCourse.setCourse(course);
        studentCourse.setStudentGrade(productRegistrationRequestDto.getStudentGrade());
        studentCourse.setSchool(productRegistrationRequestDto.getSchool());
        studentCourse.setParentEmail(productRegistrationRequestDto.getParentEmail());
        studentCourse.setParentNumber(productRegistrationRequestDto.getParentNumber());
        return studentCourse;
    }

    public StudentCourseId createStudentCourseId(Integer studentId, Integer courseId) {
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);
        return id;
    }
}
