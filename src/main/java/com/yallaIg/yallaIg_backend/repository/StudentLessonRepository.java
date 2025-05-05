package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.StudentLesson;
import com.yallaIg.yallaIg_backend.model.StudentLessonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLessonRepository extends JpaRepository<StudentLesson, StudentLessonId> {
}
