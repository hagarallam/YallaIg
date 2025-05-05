package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Page<Post> findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum approvalStatusEnum, Pageable pageable);

    Page<Post> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Post> findAllByCreatedByOrderByCreationDateDesc(Integer userId, Pageable pageable);

    Page<Post> findAllByTitleContainingOrderByCreationDateDesc(String title, Pageable pageable);

    Page<Post> findAllByApprovalStatusApprovalStatusEnumAndTitleContainingOrderByCreationDateDesc(ApprovalStatusEnum approvalStatusEnum, String title, Pageable pageable);


    @Query(""" 
             SELECT P FROM Post P 
             INNER JOIN Comment C 
             ON C.post.postId = P.postId 
             WHERE  C.approvalStatus.approvalStatusEnum = :approvalStatusEnum
            """)
    Page<Post> findAllPostWithSpecificCommentsApprovalStatus(@Param("approvalStatusEnum") ApprovalStatusEnum approvalStatusEnum , Pageable pageable);

}
