package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.PostRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.model.Post;
import com.yallaIg.yallaIg_backend.model.ReactablesCounts;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Page<PostResponseDto> getAllPosts(int page, int size);

    Page<PostResponseDto> getAllPostsByUser(int page, int size);

    Page<PostResponseDto> getAllPostsByTitle(String title, int page, int size);

    PostResponseDto findPostById(Integer id);

    Page<PostResponseDto> getAllPendingPosts(int page, int size);

    Page<PostResponseDto> getAllPostsWithPendingComments(int page, int size);

    Integer createPost(PostRequestDto postRequestDto);

    void updatePost(Integer id, PostRequestDto postRequestDto);

    void deletePost(Integer id);

    Long togglePostReaction(Integer id, ReactionType reactionType);


    void approvePost(Integer postId);

    void rejectPost(Integer postId);

    boolean isPendingPost(Post post);

    }
