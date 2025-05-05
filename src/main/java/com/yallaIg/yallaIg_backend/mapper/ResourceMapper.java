package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.request.ResourceRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentResourceResponseDto;
import com.yallaIg.yallaIg_backend.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = FileMapper.class)
public interface ResourceMapper {

    @Mapping(target = "resourcesCategoryEnum" , source = "resourcesCategory.resourcesCategoryEnum")
    ResourceResponseDto resourceToResourceResponseDto(Resource resource);

    @Mapping(target = "resourcesCategoryEnum" , source = "resourcesCategory.resourcesCategoryEnum")
    StudentResourceResponseDto resourceToStudentResourceResponseDto(Resource resource);

    Resource resourceRequestDtoToResource(ResourceRequestDto resourceRequestDto);
}
