package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.model.StudentResource;
import com.yallaIg.yallaIg_backend.model.StudentResourceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentResourceRepository extends JpaRepository<StudentResource, StudentResourceId> {

    @Query("SELECT sr.studentResourceId.resourceId FROM StudentResource sr WHERE " +
            "sr.studentResourceId.studentId = :userId ")
    List<Integer> findAllByStudentId(Integer userId);

    @Query("SELECT sr.studentResourceId.resourceId FROM StudentResource sr WHERE " +
            "sr.studentResourceId.studentId = :userId AND sr.studentResourceId.resourceId = :resourceId")
    Integer findByStudentIdAndResourceId(Integer userId,Integer resourceId);

    Optional<StudentResource> findByStudentResourceIdStudentIdAndStudentResourceIdResourceId(Integer userId, Integer resourceId);
}
