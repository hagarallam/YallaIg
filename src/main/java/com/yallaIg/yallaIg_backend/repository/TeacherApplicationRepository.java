package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.TeacherApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherApplicationRepository extends JpaRepository<TeacherApplication,Integer> {
    TeacherApplication findByEmail(String email);
}
