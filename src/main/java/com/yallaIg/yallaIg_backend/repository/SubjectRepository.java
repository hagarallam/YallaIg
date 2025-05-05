package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.RoleEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.model.Role;
import com.yallaIg.yallaIg_backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer>  {

    Optional<Subject> findBySubjectEnum(SubjectEnum subjectEnum);
}
