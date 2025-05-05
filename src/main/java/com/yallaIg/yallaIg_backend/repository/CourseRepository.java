package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {


    List<Course> findAllByCourseIdIn(List<Integer> ids);
}
