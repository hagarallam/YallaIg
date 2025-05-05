package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog,Integer> {

    Page<Blog> findAllByBlogSubjectCategorySubjectEnum(SubjectEnum subjectEnum, Pageable pageable);
}
