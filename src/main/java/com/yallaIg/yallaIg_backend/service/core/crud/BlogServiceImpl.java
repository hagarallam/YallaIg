package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.BlogRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.BlogResponseDto;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.BlogMapper;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.model.Blog;
import com.yallaIg.yallaIg_backend.repository.BlogRepository;
import com.yallaIg.yallaIg_backend.repository.SubjectRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final FileMapper fileMapper;
    private final SubjectRepository subjectRepository;

    @Override
    public Page<BlogResponseDto> getAllBlogs(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        return blogs.map(blogMapper::blogToBlogResponseDto);
    }

    @Override
    public Page<BlogResponseDto> getBlogByCategory(SubjectEnum subjectEnum, int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Blog> blogs = blogRepository.findAllByBlogSubjectCategorySubjectEnum(subjectEnum,pageable);
        return blogs.map(blogMapper::blogToBlogResponseDto);
    }

    @Override
    public BlogResponseDto getBlogById(int id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(blogMapper::blogToBlogResponseDto).orElseThrow( () -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public Integer createBlog(BlogRequestDto blogRequestDto) {
        Blog blog = blogMapper.blogRequestDtoToBlog(blogRequestDto);
        blog.setBlogSubjectCategory(subjectRepository.findBySubjectEnum(blogRequestDto.getBlogCategory()).get());
        return blogRepository.save(blog).getBlogId();
    }

    @Override
    public void updateBlog(Integer id, BlogRequestDto blogRequestDto) {
        Optional<Blog> blog = blogRepository.findById(id);
        if(blog.isPresent()){
            blogRepository.save(updateBlogData(blog.get(),blogRequestDto));
            return;
        }
        throw new ItemNotFoundException(ErrorConstants.ERROR_ITEM_NOT_FOUND);

    }

    private Blog updateBlogData(Blog blog, BlogRequestDto blogRequestDto) {
        blog.setTitle(blogRequestDto.getTitle());
        blog.setContent(blogRequestDto.getContent());
        blog.setBlogFile(fileMapper.multipartFileToFile(blogRequestDto.getBlogFile()));
        blog.setBlogSubjectCategory(subjectRepository.findBySubjectEnum(blogRequestDto.getBlogCategory()).get());
        return blog;
    }


    @Override
    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<SubjectEnum> getBlogCategories() {
        return List.of(SubjectEnum.values());
    }


}
