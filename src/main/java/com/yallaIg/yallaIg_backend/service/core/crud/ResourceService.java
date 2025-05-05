package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceResponseDto;
import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ResourceService {
    Page<ResourceResponseDto> getAllResources(int page, int size);

    Page<ResourceResponseDto> getAllResourcesByCategory(ResourcesCategoryEnum resourcesCategoryEnum, int page, int size);

    Page<ResourceResponseDto> getAllResourcesByUser(int page , int size);

    ResourceResponseDto getResourceById(int id);

    Integer createResource(ResourceRequestDto resourceRequestDto);

    void updateResource(Integer id, ResourceRequestDto resourceRequestDto);

    void deleteResource(Integer id);

    List<ResourcesCategoryEnum> getResourceCategories();



}
