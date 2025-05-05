package com.yallaIg.yallaIg_backend.dto.response;

import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponseDto {

    private int postId;
    private String title;
    private String content;
    private FileResponseDto postFile;
    private CommunityUserDto communityUserDto;
    private Date creationDate;
    private Date lastModificationDate;
    private int numberOfComments;
    private Long numberOfReactions;
    private ApprovalStatusEnum approvalStatusEnum;
    private boolean likedByCurrentUser ;

}
