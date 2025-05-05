package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.PostRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.mapper.PostMapper;
import com.yallaIg.yallaIg_backend.model.Post;
import com.yallaIg.yallaIg_backend.model.Reaction;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.ApprovalStatusRepository;
import com.yallaIg.yallaIg_backend.repository.PostRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FileMapper fileMapper;
    private final ReactionService reactionService;
    private final UserService userService;
    private final ApprovalStatusRepository approvalStatusRepository;
    private final UserPointsService userPointsService;

    @Override
    public Page<PostResponseDto> getAllPosts(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Post> posts = postRepository.findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum.APPROVED,pageable);
        Page<PostResponseDto> postResponseDtos = posts.map(postMapper::postToPostResponseDto);
        setLikedStatusForCurrentUser(postResponseDtos,posts);
        return postResponseDtos;
    }

    private void setLikedStatusForCurrentUser(Page<PostResponseDto> postResponseDtos, Page<Post> posts) {
        int currentUserId = SecurityUtil.getCurrentUserId();

        Set<Integer> likedPostIds = getLikedPostIds(posts, currentUserId);

        postResponseDtos.forEach(postDto ->
            postDto.setLikedByCurrentUser(likedPostIds.contains(postDto.getPostId()))
        );

    }

    private static Set<Integer> getLikedPostIds(Page<Post> posts, int currentUserId) {
        Set<Integer> likedPostIds = posts.stream()
                .flatMap(post -> post.getReactions().stream())
                .filter(react -> react.getUser().getUserId().equals(currentUserId))
                .map(Reaction::getReactableId)
                .collect(Collectors.toSet());
        return likedPostIds;
    }


    @Override
    public Page<PostResponseDto> getAllPostsByUser(int page, int size) {
        Pageable pageable =  CommonUtil.getPageableObject(page,size);
        Integer userId = SecurityUtil.getCurrentUserId();
        Page<Post> posts = postRepository.findAllByCreatedByOrderByCreationDateDesc(userId,pageable);
        Page<PostResponseDto> postResponseDtos = posts.map(postMapper::postToPostResponseDto);
        setLikedStatusForCurrentUser(postResponseDtos,posts);
        return postResponseDtos;
    }

    @Override
    public Page<PostResponseDto> getAllPostsByTitle(String title, int page, int size) {
        Page<Post> posts = null ;
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        if(Objects.nonNull(title)){
            posts = postRepository.findAllByApprovalStatusApprovalStatusEnumAndTitleContainingOrderByCreationDateDesc(ApprovalStatusEnum.APPROVED,title,pageable);
        }
        else{
            posts = postRepository.findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum.APPROVED,pageable);

        }
        Page<PostResponseDto> postResponseDtos = posts.map(postMapper::postToPostResponseDto);
        setLikedStatusForCurrentUser(postResponseDtos,posts);
        return postResponseDtos;
    }

    @Override
    public Page<PostResponseDto> getAllPendingPosts(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Post> pendingPosts = postRepository.findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum.PENDING,pageable);
        Page<PostResponseDto> postResponseDtos = pendingPosts.map(postMapper::postToPostResponseDto);
        setLikedStatusForCurrentUser(postResponseDtos,pendingPosts);
        return postResponseDtos;
    }

    @Override
    public Page<PostResponseDto> getAllPostsWithPendingComments(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Post> posts = postRepository.findAllPostWithSpecificCommentsApprovalStatus(ApprovalStatusEnum.PENDING,pageable);
        Page<PostResponseDto> postResponseDtos = posts.map(postMapper::postToPostResponseDto);
        setLikedStatusForCurrentUser(postResponseDtos,posts);
        return postResponseDtos;
    }

    @Override
    public PostResponseDto findPostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);

        if(post.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);

        PostResponseDto postResponseDto = postMapper.postToPostResponseDto(post.get());
        setLikedStatusForCurrentUser(postResponseDto,post.get());
        return postResponseDto;
    }
    private void setLikedStatusForCurrentUser(PostResponseDto postResponseDtos, Post post) {
        int currentUserId = SecurityUtil.getCurrentUserId();
        boolean likedPostByCurrentUser = post.getReactions().stream().anyMatch(reaction -> reaction.getUser().getUserId().equals(currentUserId));
        postResponseDtos.setLikedByCurrentUser(likedPostByCurrentUser);
    }

    @Override
    public Integer createPost(PostRequestDto postRequestDto) {
        Post post = postMapper.postRequestDtoToPost(postRequestDto);
        post.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());
        return postRepository.save(post).getPostId();
    }

    @Override
    public void updatePost(Integer id, PostRequestDto postRequestDto) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            postRepository.save(updatePostData(post.get(),postRequestDto));
            return;
        }

        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }

    private Post updatePostData(Post post,PostRequestDto postRequestDto) {
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setPostFile(fileMapper.multipartFileToFile(postRequestDto.getPostFile()));
        post.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());
        return post;
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Long togglePostReaction(Integer postId, ReactionType reactionType) {
        User user = userService.findByUserId(SecurityUtil.getCurrentUserId());
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
        long currentCount = post.get().getReactions().size();
        Optional<Reaction> optionalReaction = reactionService.getReactionByReactableIdAndUserId(postId, ReactableType.POST,user.getUserId());

        if(optionalReaction.isPresent()){
           reactionService.deleteReaction(optionalReaction.get().getReactionId());
            userPointsService.decreaseUserPoint(post.get().getUser(), PointSettingEnum.LIKE_POST);
            currentCount--;
        }
        else{
            reactionService.saveNewReaction(post.get().getPostId(), user,ReactableType.POST, reactionType);
            userPointsService.increaseUserPoint(post.get().getUser(), PointSettingEnum.LIKE_POST);
            currentCount++;
        }
        return currentCount;
    }

    @Override
    public void approvePost(Integer postId) {

        Post post = getPostById(postId);

        updatePostApprovalStatus(post, ApprovalStatusEnum.APPROVED);

    }


    @Override
    public void rejectPost(Integer postId) {

        Post post = getPostById(postId);

        updatePostApprovalStatus(post, ApprovalStatusEnum.REJECTED);

    }


    private Post getPostById(Integer postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent())
            return optionalPost.get();
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }

    private void updatePostApprovalStatus(Post post, ApprovalStatusEnum approvalStatusEnum) {
        if(isPendingPost(post)){
            post.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(approvalStatusEnum).get());
            postRepository.save(post);
        }
        else{
            // approve or rejection action is called twice , so undo
            post.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());
            postRepository.save(post);
        }

    }

    public boolean isPendingPost(Post post) {
        return ApprovalStatusEnum.PENDING.equals(post.getApprovalStatus().getApprovalStatusEnum());
    }
}
