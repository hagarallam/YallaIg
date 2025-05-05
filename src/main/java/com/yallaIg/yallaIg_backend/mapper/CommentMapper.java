package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.CommentRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CommentResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.model.Comment;
import com.yallaIg.yallaIg_backend.model.Post;
import com.yallaIg.yallaIg_backend.model.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = FileMapper.class)
public interface CommentMapper {

    @Mapping(target = "commentId",ignore = true)
    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);

    @Mapping(target = "postId",source = "post.postId")
    @Mapping(target = "communityUserDto",source = "user")
    @Mapping(target = "numberOfReactions",expression = "java(getReactionsSize(comment.getReactions()))")
    @Mapping(target = "approvalStatusEnum" ,  source = "approvalStatus.approvalStatusEnum")
    CommentResponseDto commentToCommentResponseDto(Comment comment);

    default Long getReactionsSize(List<Reaction> reactions) {
        return (long) reactions.size();
    }

}
