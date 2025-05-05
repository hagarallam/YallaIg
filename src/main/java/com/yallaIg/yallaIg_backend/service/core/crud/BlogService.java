package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.BlogRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.BlogResponseDto;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
    Page<BlogResponseDto> getAllBlogs(int page, int size);

    BlogResponseDto getBlogById(int id);

    Integer createBlog(BlogRequestDto blogRequestDto);

    void updateBlog(Integer id, BlogRequestDto blogRequestDto);

    void deleteBlog(Integer id);

    List<SubjectEnum> getBlogCategories();

    Page<BlogResponseDto> getBlogByCategory(SubjectEnum subjectEnum, int page, int size);
}
