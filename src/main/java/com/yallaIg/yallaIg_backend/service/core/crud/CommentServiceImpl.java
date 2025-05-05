package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.CommentRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CommentResponseDto;
import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.exception.GeneralValidationException;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.CommentMapper;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.model.Comment;
import com.yallaIg.yallaIg_backend.model.Post;
import com.yallaIg.yallaIg_backend.model.Reaction;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.ApprovalStatusRepository;
import com.yallaIg.yallaIg_backend.repository.CommentRepository;
import com.yallaIg.yallaIg_backend.repository.PostRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_INVALID_REQUEST;
import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final FileMapper fileMapper;
    private final UserService userService;
    private final ReactionService reactionService;
    private final ApprovalStatusRepository approvalStatusRepository;
    private final UserPointsService userPointsService;

    @Override
    public Page<CommentResponseDto> getAllComments(int page, int size, int postId) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Comment> comments = commentRepository.findAllByApprovalStatusApprovalStatusEnumAndPostPostIdOrderByCreationDateDesc(ApprovalStatusEnum.APPROVED,postId,pageable);
        Page<CommentResponseDto> commentResponseDtos = comments.map(commentMapper::commentToCommentResponseDto);
        setLikedStatusByCurrentUser(commentResponseDtos,comments);
        return commentResponseDtos;
    }

    private void setLikedStatusByCurrentUser(Page<CommentResponseDto> commentResponseDtos, Page<Comment> comments) {
        int currentUserId = SecurityUtil.getCurrentUserId();

        Set<Integer> likedCommentIds = getLikedCommentsIds(comments, currentUserId);

        commentResponseDtos.forEach(comment -> comment.setLikedByCurrentUser(
                likedCommentIds.contains(comment.getCommentId())));
    }

    private static Set<Integer> getLikedCommentsIds(Page<Comment> comments, int currentUserId) {
        return comments.stream()
                .flatMap(comment -> comment.getReactions().stream())
                .filter(react -> react.getUser().getUserId().equals(currentUserId))
                .map(Reaction::getReactableId)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<CommentResponseDto> getAllPendingComments(int page, int size, Integer postId) {
        Page<Comment> comments = null;
        Pageable pageable = CommonUtil.getPageableObject(page,size);

        if(Objects.nonNull(postId) && postId>0){
            comments = commentRepository.findAllByApprovalStatusApprovalStatusEnumAndPostPostIdOrderByCreationDateDesc(ApprovalStatusEnum.PENDING,postId,pageable);
        }
        else{
            comments = commentRepository.findAllByApprovalStatusApprovalStatusEnumOrderByCreationDateDesc(ApprovalStatusEnum.PENDING,pageable);

        }
        Page<CommentResponseDto> commentResponseDtos = comments.map(commentMapper::commentToCommentResponseDto);
        setLikedStatusByCurrentUser(commentResponseDtos,comments);
        return commentResponseDtos;
    }

    @Override
    public Page<CommentResponseDto> getAllCommentsByUser(int page, int size) {
        Integer userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Comment> comments = commentRepository.findAllByCreatedByOrderByCreationDateDesc(userId,pageable);
        Page<CommentResponseDto> commentResponseDtos = comments.map(commentMapper::commentToCommentResponseDto);
        setLikedStatusByCurrentUser(commentResponseDtos,comments);
        return commentResponseDtos;
    }

    @Override
    public CommentResponseDto findCommentById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        CommentResponseDto commentResponseDto = comment.map(commentMapper::commentToCommentResponseDto).orElseThrow(() -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
        setLikedStatusByCurrentUser(commentResponseDto,comment.get());
        return commentResponseDto;
    }


    private void setLikedStatusByCurrentUser(CommentResponseDto commentResponseDto, Comment comment) {
        int currentUserId = SecurityUtil.getCurrentUserId();
        boolean likedByCurrentUser = comment.getReactions().stream().anyMatch(react -> react.getUser().getUserId().equals(currentUserId));
        commentResponseDto.setLikedByCurrentUser(likedByCurrentUser) ;
    }

    @Override
    public Integer createComment(CommentRequestDto commentRequestDto) {
        Post post = getPost(commentRequestDto);

        if(!ApprovalStatusEnum.isApproved(post.getApprovalStatus().getApprovalStatusEnum())){
            throw new GeneralValidationException(ERROR_INVALID_REQUEST);
        }

        Comment comment = commentMapper.commentRequestDtoToComment(commentRequestDto);
        comment.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());

        //handle many-to-one relationship
        mapPostToComment(comment,post);
        return commentRepository.save(comment).getCommentId();
    }

    private Post getPost(CommentRequestDto commentRequestDto) {
        Optional<Post> post = postRepository.findById(commentRequestDto.getPostId());
        if(post.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
        return post.get();
    }

    private void mapPostToComment(Comment comment, Post post) {
        comment.setPost(post);
    }

    @Override
    public void updateComment(Integer id, CommentRequestDto commentRequestDto) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()){
            commentRepository.save(updateCommentData(comment.get(),commentRequestDto));
            return;
        }
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }

    private Comment updateCommentData(Comment comment, CommentRequestDto commentRequestDto) {
        comment.setContent(commentRequestDto.getContent());
        comment.setCommentFile(fileMapper.multipartFileToFile(commentRequestDto.getCommentFile()));
        comment.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());
        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public long toggleCommentReaction(int commentId, ReactionType reactionType) {
        User user = userService.findByUserId(SecurityUtil.getCurrentUserId());
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
        long currentCount = comment.get().getReactions().size();
        Optional<Reaction> optionalReaction = reactionService.getReactionByReactableIdAndUserId(commentId, ReactableType.COMMENT,user.getUserId());

        if(optionalReaction.isPresent()){
            reactionService.deleteReaction(optionalReaction.get().getReactionId());
            userPointsService.decreaseUserPoint(comment.get().getUser(), PointSettingEnum.LIKE_COMMENT);
            currentCount--;
        }
        else{
            reactionService.saveNewReaction(comment.get().getCommentId(), user,ReactableType.COMMENT, reactionType);
            userPointsService.increaseUserPoint(comment.get().getUser(), PointSettingEnum.LIKE_COMMENT);
            currentCount++;
        }
        return currentCount;
    }

    @Override
    public void approveComment(Integer commentId) {

        Comment comment = getCommentById(commentId);

        updateCommentApprovalStatus(comment,ApprovalStatusEnum.APPROVED);
    }


    @Override
    public void rejectComment(Integer commentId) {

        Comment comment = getCommentById(commentId);

        updateCommentApprovalStatus(comment,ApprovalStatusEnum.REJECTED);

    }

    private Comment getCommentById(Integer commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
        return optionalComment.get();
    }

    private void updateCommentApprovalStatus(Comment comment,ApprovalStatusEnum approvalStatusEnum) {
        if(isPendingComment(comment)){
            comment.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(approvalStatusEnum).get());
            commentRepository.save(comment);
        }
        else{
            comment.setApprovalStatus(approvalStatusRepository.findByApprovalStatusEnum(ApprovalStatusEnum.PENDING).get());
            commentRepository.save(comment);
        }
    }

    private static boolean isPendingComment(Comment comment) {
        return ApprovalStatusEnum.PENDING.equals(comment.getApprovalStatus().getApprovalStatusEnum());
    }
}
