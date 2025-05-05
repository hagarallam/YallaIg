package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.CommentRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.CommentResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.service.core.crud.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/community/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<CommentResponseDto>> getAllComments(@RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "10") Integer size,
                                                                                     @RequestParam Integer postId){
        Page<CommentResponseDto> comments = commentService.getAllComments(page,size,postId);
        GenericApiResponsePage<CommentResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(comments);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<CommentResponseDto>> getCommentById(@PathVariable Integer id){
        CommentResponseDto commentResponseDto = commentService.findCommentById(id);
        GenericApiResponse<CommentResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(commentResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<GenericApiResponsePage<CommentResponseDto>> getAllPendingComments(@RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "10") Integer size,
                                                                                     @RequestParam(required = false) Integer postId){
        Page<CommentResponseDto> comments = commentService.getAllPendingComments(page,size,postId);
        GenericApiResponsePage<CommentResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(comments);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @GetMapping("/user-comments")
    public ResponseEntity<GenericApiResponsePage<CommentResponseDto>> getAllCommentsByUser(@RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "10") Integer size){
        Page<CommentResponseDto> comments = commentService.getAllCommentsByUser(page,size);
        GenericApiResponsePage<CommentResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(comments);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> createComment(@Valid @ModelAttribute CommentRequestDto commentRequestDto){
        Integer id = commentService.createComment(commentRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_COMMENT_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updateComment(@PathVariable Integer id,
                                                         @Valid @ModelAttribute CommentRequestDto commentRequestDto){
        commentService.updateComment(id,commentRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COMMENT_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteComment(@PathVariable Integer id){
        commentService.deleteComment(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COMMENT_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @PostMapping("/{id}/useful")
    public ResponseEntity<GenericApiResponse<Long>> toggleCommentReaction(@PathVariable(name = "id") Integer commentId){
        long commentReactionCount = commentService.toggleCommentReaction(commentId, ReactionType.USEFUL);
        GenericApiResponse<Long> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(commentReactionCount);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<GenericApiResponse> approveComment(@PathVariable(name = "id") Integer commentId){
        commentService.approveComment(commentId);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COMMENT_APPROVAL);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<GenericApiResponse> rejectComment(@PathVariable(name = "id") Integer commentId){
        commentService.rejectComment(commentId);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_COMMENT_REJECTION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }



}
