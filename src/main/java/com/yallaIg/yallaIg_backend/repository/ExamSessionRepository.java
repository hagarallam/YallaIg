package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.enums.LevelEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.model.Blog;
import com.yallaIg.yallaIg_backend.model.ExamSession;
import com.yallaIg.yallaIg_backend.model.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamSessionRepository extends JpaRepository<ExamSession,Integer> {

    Optional<ExamSession> findByExamSessionValue(ExamSessionEnum examSessionEnum);

}
