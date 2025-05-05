package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDto {

    private Integer commentId;
    private Integer postId;
    private String content ;
    private Long numberOfReactions;
    private FileResponseDto commentFile;
    private CommunityUserDto communityUserDto;
    private Date creationDate;
    private Date lastModificationDate;
    private ApprovalStatusEnum approvalStatusEnum;
    private boolean likedByCurrentUser ;
}
