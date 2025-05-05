package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.StudentCourse;
import com.yallaIg.yallaIg_backend.model.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {

    List<StudentCourse> findAllByStudentCourseIdStudentId(Integer userId);

    List<StudentCourse> findAllByOrderOrderId(Integer orderId);

    List<StudentCourse> findByCreationDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<StudentCourse> findByStudentCourseIdStudentIdAndStudentCourseIdCourseId(Integer userId, Integer courseId);
}
