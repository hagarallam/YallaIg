package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Integer> {

    Page<Comment> findAllByPostPostIdOrderByCreationDateDesc(int postId, Pageable pageable);

    Page<Comment> findAllByApprovalStatusApprovalStatusEnumAndPostPostIdOrderByCreationDateDesc(ApprovalStatusEnum approvalStatusEnum, int postId, Pageable pageable);

    Page<Comment> findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum approvalStatusEnum, Pageable pageable);

    Page<Comment> findAllByCreatedByOrderByCreationDateDesc(Integer userId, Pageable pageable);
}
