package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.CommentRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CommentResponseDto;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentResponseDto> getAllComments(int page, int size,int postId);

    CommentResponseDto findCommentById(Integer id);

    Page<CommentResponseDto> getAllPendingComments(int page, int size, Integer postId);

    Page<CommentResponseDto> getAllCommentsByUser(int page, int size);

    Integer createComment(CommentRequestDto commentRequestDto);

    void updateComment(Integer id, CommentRequestDto commentRequestDto);

    void deleteComment(Integer id);

    long toggleCommentReaction(int commentId, ReactionType reactionType);

    void approveComment(Integer commentId);

    void rejectComment(Integer commentId);
}
