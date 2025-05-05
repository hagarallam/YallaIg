package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.BlogRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.*;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.service.core.crud.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<BlogResponseDto>> getAllBlogs(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size){
        Page<BlogResponseDto> blogResponseDtoPage = blogService.getAllBlogs(page,size);
        GenericApiResponsePage<BlogResponseDto> genericApiResponsePage = new GenericApiResponsePage<>();
        genericApiResponsePage.setPayload(blogResponseDtoPage);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }
    @GetMapping("/category")
    public ResponseEntity<GenericApiResponsePage<BlogResponseDto>> getAllBlogsByCategory(@RequestParam("category") SubjectEnum subjectEnum,
                                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                                         @RequestParam(defaultValue = "10") Integer size){
        Page<BlogResponseDto> blogResponseDto = blogService.getBlogByCategory(subjectEnum,page,size);
        GenericApiResponsePage<BlogResponseDto> genericApiResponse = new GenericApiResponsePage<>();
        genericApiResponse.setPayload(blogResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<BlogResponseDto>> getBlogById(@PathVariable int id){
        BlogResponseDto blogResponseDto = blogService.getBlogById(id);
        GenericApiResponse<BlogResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(blogResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }


    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> createBlog(@Valid @ModelAttribute BlogRequestDto blogRequestDto){
        Integer id = blogService.createBlog(blogRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_BLOG_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updateBlog(@PathVariable Integer id,
                                                         @Valid @ModelAttribute BlogRequestDto blogRequestDto){
        blogService.updateBlog(id,blogRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_BLOG_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteBlog(@PathVariable Integer id){
        blogService.deleteBlog(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_BLOG_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<GenericApiResponseList<SubjectEnum>> getBlogsCategory(){
        List<SubjectEnum> subjectEnums = blogService.getBlogCategories();
        GenericApiResponseList<SubjectEnum> genericApiResponseList = new GenericApiResponseList<>();
        genericApiResponseList.setPayload(subjectEnums);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);
    }

}
