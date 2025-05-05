package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.BlogRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.BlogResponseDto;
import com.yallaIg.yallaIg_backend.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = FileMapper.class)
public interface BlogMapper {

    @Mapping(target = "blogCategory" ,source = "blogSubjectCategory.subjectEnum")
    BlogResponseDto blogToBlogResponseDto(Blog blog);

    Blog blogRequestDtoToBlog(BlogRequestDto blogRequestDto);
}
