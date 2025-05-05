package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.PostRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.model.Comment;
import com.yallaIg.yallaIg_backend.model.Post;
import com.yallaIg.yallaIg_backend.model.Reaction;
import com.yallaIg.yallaIg_backend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring",uses = {FileMapper.class,UserMapper.class})
public interface PostMapper {
    
    @Mapping(target = "postId",ignore = true)
    @Mapping(target = "comments" , ignore = true)
    Post postRequestDtoToPost(PostRequestDto postRequestDto);

    @Mapping(target = "communityUserDto",source = "user")
    @Mapping(target = "numberOfComments",expression = "java(getCommentsSize(post.getComments()))")
    @Mapping(target = "numberOfReactions", expression = "java(getReactionsSize(post.getReactions()))")
    @Mapping(target = "approvalStatusEnum" ,  source = "approvalStatus.approvalStatusEnum")
    PostResponseDto postToPostResponseDto(Post post);

    default int getCommentsSize(List<Comment> comments) {
        return comments.stream().filter(com->
                ApprovalStatusEnum.APPROVED.equals(com.getApprovalStatus().getApprovalStatusEnum())).toList().size();
    }
    default Long getReactionsSize(List<Reaction> reactions) {
        return (long) reactions.size();
    }

}

