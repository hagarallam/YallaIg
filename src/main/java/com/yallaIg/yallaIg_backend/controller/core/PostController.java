package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.PostRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponse;
import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.PostResponseDto;
import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.service.core.crud.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/community/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size){
        Page<PostResponseDto> posts = postService.getAllPosts(page,size);
        GenericApiResponsePage<PostResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(posts);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<PostResponseDto>> getPostById(@PathVariable Integer id){
        PostResponseDto postResponseDto = postService.findPostById(id);
        GenericApiResponse<PostResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(postResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }
    @GetMapping("/user-posts")
    public ResponseEntity<GenericApiResponsePage<PostResponseDto>> getAllPostsByUser(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size){
        Page<PostResponseDto> posts = postService.getAllPostsByUser(page,size);
        GenericApiResponsePage<PostResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(posts);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericApiResponsePage<PostResponseDto>> getAllPostsByTitle(@RequestParam(name = "title",required = false) String title,
                                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "10") Integer size){
        Page<PostResponseDto> posts = postService.getAllPostsByTitle(title,page,size);
        GenericApiResponsePage<PostResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(posts);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<GenericApiResponsePage<PostResponseDto>> getAllPendingPosts(@RequestParam(defaultValue = "0") Integer page,
                                                                                            @RequestParam(defaultValue = "10") Integer size){
        Page<PostResponseDto> posts = postService.getAllPendingPosts(page,size);
        GenericApiResponsePage<PostResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(posts);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }
    @GetMapping("/pending-comments")
    public ResponseEntity<GenericApiResponsePage<PostResponseDto>> getAllPostsWithPendingComments(@RequestParam(defaultValue = "0") Integer page,
                                                                                      @RequestParam(defaultValue = "10") Integer size){
        Page<PostResponseDto> posts = postService.getAllPostsWithPendingComments(page,size);
        GenericApiResponsePage<PostResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(posts);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> createPost(@Valid @ModelAttribute PostRequestDto postRequestDto){
        Integer id = postService.createPost(postRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_POST_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updatePost(@PathVariable Integer id,
                                                           @Valid @ModelAttribute PostRequestDto postRequestDto){
        postService.updatePost(id,postRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_POST_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deletePost(@PathVariable Integer id){
        postService.deletePost(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_POST_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @PostMapping("/{id}/useful")
    public ResponseEntity<GenericApiResponse<Long>> togglePostReaction(@PathVariable Integer id){
        Long postReactions = postService.togglePostReaction(id, ReactionType.USEFUL);
        GenericApiResponse<Long> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(postReactions);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @PostMapping("/{id}/approve")
    public ResponseEntity<GenericApiResponse> approvePost(@PathVariable(name = "id") Integer postId){
        postService.approvePost(postId);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_POST_APPROVAL);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<GenericApiResponse> rejectPost(@PathVariable(name = "id") Integer postId){
        postService.rejectPost(postId);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_POST_REJECTION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

}
